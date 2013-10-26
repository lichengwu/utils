package cn.lichengwu.utils.collection;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 存储基本类型的hashMap，内存空间占用小
 * 
 * @see java.util.concurrent.ConcurrentHashMap
 * 
 * @author yusheng.hxh
 */
public class ConcurrentOriginalHashMap {
	/**
	 * The maximum number of segments to allow; used to bound constructor
	 * arguments.
	 */
	static final int MAX_SEGMENTS = 1 << 16; // slightly conservative

	/**
	 * The maximum capacity
	 */
	static final int MAXIMUM_CAPACITY = 1 << 30;

	private Segment[] segments;

	private int segmentShift;
	private int segmentMask;

	public ConcurrentOriginalHashMap(int size) {
		init(size, 16);
	}

	public ConcurrentOriginalHashMap(int size, int concurrencyLevel) {
		init(size, concurrencyLevel);
	}

	protected void init(int initialCapacity, int concurrencyLevel) {
		if (initialCapacity < 0 || concurrencyLevel <= 0)
			throw new IllegalArgumentException();

		if (concurrencyLevel > MAX_SEGMENTS)
			concurrencyLevel = MAX_SEGMENTS;
		// Find power-of-two sizes best matching arguments
		int sshift = 0;
		int ssize = 1;
		while (ssize < concurrencyLevel) {
			++sshift;
			ssize <<= 1;
		}
		segmentShift = 32 - sshift;
		segmentMask = ssize - 1;
		segments = new Segment[ssize];

		if (initialCapacity > MAXIMUM_CAPACITY)
			initialCapacity = MAXIMUM_CAPACITY;
		int c = initialCapacity / ssize;
		if (c * ssize < initialCapacity)
			++c;
		int cap = 1;
		while (cap < c)
			cap <<= 1;

		for (int index = 0; index < ssize; index++) {
			segments[index] = new Segment(cap);
		}
	}

	public static int hash(long h) {
		return hash((int) (h ^ (h >>> 32)));
	}

	final Segment segmentFor(int hash) {
		return segments[(hash >>> segmentShift) & segmentMask];
	}

	public static int hash(int h) {
		// Spread bits to regularize both segment and index locations,
		// using variant of single-word Wang/Jenkins hash.
		h += (h << 15) ^ 0xffffcd7d;
		h ^= (h >>> 10);
		h += (h << 3);
		h ^= (h >>> 6);
		h += (h << 2) + (h << 14);
		return h ^ (h >>> 16);
	}

	public int get(long key) {
		int hash = hash(key);
		return segmentFor(hash).get(key, hash);
	}

	public int put(long key, int value) {
		int hash = hash(key);
		return segmentFor(hash).put(key, value, hash);
	}

	public int remove(long key) {
		int hash = hash(key);
		return segmentFor(hash).remove(key, hash);
	}

	/**
	 * 遍历所有value删除，低效
	 */
	public void removeByValue(int value) {
		for (int index = 0, length = segments.length; index < length; index++) {
			Segment block = segments[index];
			block.removeByValue(value);
		}
	}

	public static int count = 0;

	static final class Segment extends ReentrantLock {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3049867739177965911L;
		private DataStore store;
		private int storeLength;

		public Segment(int size) {
			this.store = new DataStore(size);
			this.storeLength = store.getLength();
		}

		protected int getKeyIndex(long key, int hash) {
			int startIndex = hash & (storeLength - 1);
			return getKeyIndex(startIndex, key);
		}

		protected int getKeyIndex(int startIndex, long key) {
			for (int index = startIndex; index < storeLength; index++) {// 一直向后查找
				long currentKey = store.getKey(index);
				if (currentKey == key) {
					return index;
				}
			}
			for (int index = 0; index < startIndex; index++) {// 一直向后查找
				long currentKey = store.getKey(index);
				if (currentKey == key) {
					return index;
				}
			}
			return -1;
		}

		public int get(long key, int hash) {
			int keyIndex = getKeyIndex(key, hash);
			if (-1 != keyIndex) {
				return store.getValue(keyIndex);
			}
			return 0;
		}

		public int put(long key, int value, int hash) {
			lock();
			try {
				int startIndex = hash & (storeLength - 1);
				for (int index = startIndex, length = storeLength; index < length; index++) {// 一直向后查找
					long currentKey = store.getKey(index);
					if (currentKey == 0) {// 如果位置没有被占用
						store.set(key, value, index);
						return 0;
					}
					if (currentKey == key) {// 如果是原来已经有值的
						int oldValue = store.getValue(index);
						store.set(key, value, index);
						return oldValue;
					}
					if (index == (startIndex + 1))
						count++;
				}
				for (int index = 0; index < startIndex; index++) {// 从开头向后找
					long currentKey = store.getKey(index);
					if (currentKey == 0) {// 如果位置没有被占用
						store.set(key, value, index);
						return 0;
					}
					if (currentKey == key) {// 如果是原来已经有值的
						int oldValue = store.getValue(index);
						store.set(key, value, index);
						return oldValue;
					}
				}
				throw new IllegalStateException("Block is full!");
			} finally {
				unlock();
			}
		}

		public int remove(long key, int hash) {
			lock();
			try {
				int keyIndex = getKeyIndex(key, hash);
				if (-1 != keyIndex) {
					int oldValue = store.getValue(keyIndex);
					store.set(0, 0, keyIndex);
					return oldValue;
				}
				return 0;
			} finally {
				unlock();
			}
		}

		/**
		 * 遍历所有value删除，低效
		 */
		public void removeByValue(int value) {
			lock();
			try {
				store.removeByValue(value);
			} finally {
				unlock();
			}
		}

	}

	static final class DataStore implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8898069955347644045L;
		private long[] keys;
		private int[] values;

		public DataStore(int length) {
			keys = new long[length];
			values = new int[length];
		}

		public long getKey(int index) {
			return keys[index];
		}

		public int getValue(int index) {
			return values[index];
		}

		public void set(long key, int value, int index) {
			keys[index] = key;
			values[index] = value;
		}

		public int getLength() {
			return keys.length;
		}

		public void removeByValue(int value) {
			for (int index = 0, length = values.length; index < length; index++) {
				int currentValue = values[index];
				if (currentValue == value) {
					set(0, 0, index);
				}
			}
		}

	}

}
