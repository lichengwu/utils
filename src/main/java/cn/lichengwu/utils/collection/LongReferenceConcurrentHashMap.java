package cn.lichengwu.utils.collection;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 并发map改版，减少内存占用
 *
 * @author 佐井
 * @version 1.0
 * @created 2013-09-06 9:58 PM
 */
public class LongReferenceConcurrentHashMap<V> implements Map<Long, V>, Serializable {

    private static final long serialVersionUID = -4826459228149190787L;

    /**
     * The default initial capacity for this table,
     * used when not otherwise specified in a constructor.
     */
    static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * The default load factor for this table, used when not
     * otherwise specified in a constructor.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * The default concurrency level for this table, used when not
     * otherwise specified in a constructor.
     */
    static final int DEFAULT_CONCURRENCY_LEVEL = 4;

    /**
     * The maximum capacity, used if a higher value is implicitly
     * specified by either of the constructors with arguments.  MUST
     * be a power of two <= 1<<30 to ensure that entries are indexable
     * using ints.
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final int INVALID_INDEX = -1;

    static final long EMPTY_SLOT = 0;

    /**
     * The maximum number of segments to allow; used to bound
     * constructor arguments.
     */
    static final int MAX_SEGMENTS = 1 << 16; // slightly conservative

    /**
     * Number of unsynchronized retries in size and containsValue
     * methods before resorting to locking. This is used to avoid
     * unbounded retries if tables undergo continuous modification
     * which would make it impossible to obtain an accurate result.
     */
    static final int RETRIES_BEFORE_LOCK = 2;


    /**
     * Mask value for indexing into segments. The upper bits of a
     * key's hash code are used to choose the segment.
     */
    private final int segmentMask;

    /**
     * Shift value for indexing within segments.
     */
    private final int segmentShift;

    /**
     * The segments, each of which is a specialized hash table
     */
    final Segment<V>[] segments;

    /**
     * Returns the segment that should be used for key with given hash
     *
     * @param hash the hash code for the key
     *
     * @return the segment
     */
    final Segment<V> segmentFor(int hash) {
        return segments[(hash >>> segmentShift) & segmentMask];
    }


    /**
     * HashEntry內部数组实现
     *
     * @param <V>
     */
    static final class HashEntry<V> implements Serializable {

        private static final long serialVersionUID = -7853779126197746481L;

        long[] keys;
        Object[] values;


        HashEntry(int size) {
            keys = new long[size];
            values = new Object[size];
        }

        long getKey(int index) {
            return keys[index];
        }

        V getValue(int index) {
            return (V) values[index];
        }

        void set(long key, V value, int index) {
            keys[index] = key;
            values[index] = value;
        }

        int getSize() {
            return keys.length;
        }

    }


    /**
     * Segments are specialized versions of hash tables.  This
     * subclasses from ReentrantLock opportunistically, just to
     * simplify some locking and avoid separate construction.
     */
    public static final class Segment<V> extends ReentrantLock implements Serializable {

        private static final long serialVersionUID = 2249069246763182397L;

        /**
         * The number of elements in this segment's region.
         */
        transient volatile int count;

        /**
         * Number of updates that alter the size of the table. This is
         * used during bulk-read methods to make sure they see a
         * consistent snapshot: If modCounts change during a traversal
         * of segments computing size or checking containsValue, then
         * we might have an inconsistent view of state so (usually)
         * must retry.
         */
        transient int modCount;

        /**
         * The table is rehashed when its size exceeds this threshold.
         * (The value of this field is always <tt>(int)(capacity *
         * loadFactor)</tt>.)
         */
        transient int threshold;

        /**
         * The per-segment table.
         */
        transient volatile HashEntry<V> table;

        /**
         * The load factor for the hash table.  Even though this value
         * is same for all segments, it is replicated to avoid needing
         * links to outer object.
         *
         * @serial
         */
        public Segment(int initialCapacity) {
            table = new HashEntry<V>(initialCapacity);
            threshold = (int) (table.getSize() * DEFAULT_LOAD_FACTOR);
        }

        protected int getKeyIndex(long key, int hash) {
            int mask = table.getSize() - 1;
            int index = hash & mask;

            int iCount = 0;

            while (iCount <= threshold) {
                long currentKey = table.getKey(index);
                if (currentKey == key) {
                    return index;
                }
                index = (index + 1) & mask;
                iCount++;
            }
            return INVALID_INDEX;
        }

        /**
         * Reads value field of an entry under lock. Called if value
         * field ever appears to be null. This is possible only if a
         * compiler happens to reorder a HashEntry initialization with
         * its table assignment, which is legal under memory model
         * but is not known to ever occur.
         */
        V readValueUnderLock(int index) {
            lock();
            try {
                return table.getValue(index);
            } finally {
                unlock();
            }
        }

        V get(long key, int hash) {
            int keyIndex = getKeyIndex(key, hash);
            if (INVALID_INDEX != keyIndex) {
                return readValueUnderLock(keyIndex);
            }
            return null;
        }

        boolean containsKey(long key, int hash) {
            return getKeyIndex(key, hash) != INVALID_INDEX;
        }

        boolean containsValue(V value) {
            if (count != 0) { // read-volatile
                HashEntry<V> tab = table;
                int len = tab.getSize();
                for (int i = 0; i < len; i++) {
                    V v = null;
                    if (v == null) // recheck
                        v = readValueUnderLock(i);
                    if (value.equals(v))
                        return true;
                }
            }
            return false;
        }

        V put(long key, int hash, V value) {
            lock();
            try {
                int c = count;
                // ensure capacity
                if (c++ > threshold) {
                    rehash();
                }

                int mask = table.getSize() - 1;
                int index = hash & mask;
                int iCount = 0;
                while (iCount <= threshold) {
                    long currentKey = table.getKey(index);
                    //存在空位置，插入
                    if (currentKey == EMPTY_SLOT) {
                        table.set(key, value, index);
                        ++modCount;
                        count = c;
                        return null;
                    } else if (currentKey == key) {
                        //替换
                        V oldValue = table.getValue(index);
                        table.set(key, value, index);
                        return oldValue;
                    }
                    index = (index + 1) & mask;
                    iCount++;
                }

                throw new IllegalStateException("here is a bug!");

            } finally {
                unlock();
            }
        }

        /**
         * 重建hash
         */
        void rehash() {
            //copy
            HashEntry<V> oldTable = table;
            int oldCapacity = oldTable.getSize();
            if (oldCapacity >= MAXIMUM_CAPACITY) {
                //查过最大容量，由于没有entry对象了，所以无法使用链表，也就不能继续存储了
                throw new IllegalStateException("map can not bigger than " + MAXIMUM_CAPACITY);
            }

            /**
             * 重建hash结构。由于使用数组代替HashEntry，存在比较大的hash冲突。重建过程部分数据位置改变。
             */
            HashEntry<V> newTable = new HashEntry(oldCapacity << 1);
            threshold = (int) (newTable.getSize() * DEFAULT_LOAD_FACTOR);
            int sizeMask = newTable.getSize() - 1;
            for (int i = 0; i < oldCapacity; i++) {
                long key = oldTable.getKey(i);
                //空的位置，不用拷贝
                if (key == EMPTY_SLOT) {
                    continue;
                }
                int newIdx = hash(key) & sizeMask;
                boolean inserted = false;
                int iCount = 0;
                while (iCount <= threshold) {
                    long currentKey = newTable.getKey(newIdx);
                    if (currentKey == EMPTY_SLOT) {
                        newTable.set(key, oldTable.getValue(i), newIdx);
                        inserted = true;
                        break;
                    }
                    newIdx = (newIdx + 1) & sizeMask;
                    iCount++;
                }

                if (!inserted) {
                    throw new IllegalStateException("can not insert, this is a bug!");
                }
                table = newTable;
            }
        }

        /**
         * 根据key和hash删除数据，如果value不为null，则key上的value必须匹配才能删除
         *
         * @param key
         * @param hash
         * @param value
         *
         * @return 如果有对象被删除，返回被删除的对象，否则返回null
         */
        V remove(long key, int hash, V value) {
            lock();
            try {
                int c = count - 1;
                int keyIndex = getKeyIndex(key, hash);
                if (INVALID_INDEX != keyIndex) {
                    V oldValue = table.getValue(keyIndex);
                    if (value == null || value.equals(oldValue)) {
                        table.set(EMPTY_SLOT, null, keyIndex);
                        count = c;
                        return oldValue;
                    }
                    return null;
                }
                return null;
            } finally {
                unlock();
            }
        }

        /**
         * 清空当前段数据
         */
        void clear() {
            if (count != 0) {
                lock();
                try {
                    HashEntry<V> tab = table;
                    for (int i = 0; i < tab.getSize(); i++) {
                        tab.set(0, null, i);
                    }
                    ++modCount;
                    count = 0; // write-volatile
                } finally {
                    unlock();
                }
            }
        }
    }

    /**
     * Creates a new, empty map with the specified initial
     * capacity, load factor and concurrency level.
     *
     * @param initialCapacity  the initial capacity. The implementation
     *                         performs internal sizing to accommodate this many elements.
     * @param concurrencyLevel the estimated number of concurrently
     *                         updating threads. The implementation performs internal sizing
     *                         to try to accommodate this many threads.
     *
     * @throws IllegalArgumentException if the initial capacity is
     *                                  negative or the load factor or concurrencyLevel are
     *                                  nonpositive.
     */
    public LongReferenceConcurrentHashMap(int initialCapacity, int concurrencyLevel) {

        if (concurrencyLevel > MAX_SEGMENTS)
            concurrencyLevel = MAX_SEGMENTS;

        // Find power-of-two sizes best matching arguments
        int sshift = 0;
        int ssize = 1;
        while (ssize < concurrencyLevel) {
            ++sshift;
            ssize <<= 1;
        }
        try {

            segmentShift = 32 - sshift;
            segmentMask = ssize - 1;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        this.segments = new Segment[ssize];

        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        int c = initialCapacity / ssize;
        if (c * ssize < initialCapacity)
            ++c;
        int cap = 1;
        while (cap < c) {
            cap <<= 1;
        }

        for (int i = 0; i < this.segments.length; ++i) {
            this.segments[i] = new Segment<V>(cap);
        }
    }

    /**
     * Creates a new, empty map with the specified initial capacity
     * and load factor and with the default concurrencyLevel (16).
     *
     * @param initialCapacity The implementation performs internal
     *                        sizing to accommodate this many elements.
     *
     * @throws IllegalArgumentException if the initial capacity of
     *                                  elements is negative or the load factor is nonpositive
     * @since 1.6
     */
    public LongReferenceConcurrentHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_CONCURRENCY_LEVEL);
    }

    /**
     * Creates a new, empty map with a default initial capacity (16),
     * load factor (0.75) and concurrencyLevel (16).
     */
    public LongReferenceConcurrentHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_CONCURRENCY_LEVEL);
    }

    /**
     * Creates a new map with the same mappings as the given map.
     * The map is created with a capacity of 1.5 times the number
     * of mappings in the given map or 16 (whichever is greater),
     * and a default load factor (0.75) and concurrencyLevel (16).
     *
     * @param m the map
     */
    public LongReferenceConcurrentHashMap(Map<Long, ? extends V> m) {
        this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_INITIAL_CAPACITY), DEFAULT_CONCURRENCY_LEVEL);
        putAll(m);
    }

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * @return <tt>true</tt> if this map contains no key-value mappings
     */
    public boolean isEmpty() {
        final Segment<V>[] segments = this.segments;
        /*
         * We keep track of per-segment modCounts to avoid ABA
         * problems in which an element in one segment was added and
         * in another removed during traversal, in which case the
         * table was never actually empty at any point. Note the
         * similar use of modCounts in the size() and containsValue()
         * methods, which are the only other methods also susceptible
         * to ABA problems.
         */
        int[] mc = new int[segments.length];
        int mcsum = 0;
        for (int i = 0; i < segments.length; ++i) {
            if (segments[i].count != 0)
                return false;
            else
                mcsum += mc[i] = segments[i].modCount;
        }
        // If mcsum happens to be zero, then we know we got a snapshot
        // before any modifications at all were made.  This is
        // probably common enough to bother tracking.
        if (mcsum != 0) {
            for (int i = 0; i < segments.length; ++i) {
                if (segments[i].count != 0 || mc[i] != segments[i].modCount)
                    return false;
            }
        }
        return true;
    }

    /**
     * Returns the number of key-value mappings in this map.  If the
     * map contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        final Segment<V>[] segments = this.segments;
        long sum = 0;
        long check = 0;
        int[] mc = new int[segments.length];
        // Try a few times to get accurate count. On failure due to
        // continuous async changes in table, resort to locking.
        for (int k = 0; k < RETRIES_BEFORE_LOCK; ++k) {
            check = 0;
            sum = 0;
            int mcsum = 0;
            for (int i = 0; i < segments.length; ++i) {
                sum += segments[i].count;
                mcsum += mc[i] = segments[i].modCount;
            }
            if (mcsum != 0) {
                for (int i = 0; i < segments.length; ++i) {
                    check += segments[i].count;
                    if (mc[i] != segments[i].modCount) {
                        check = -1; // force retry
                        break;
                    }
                }
            }
            if (check == sum)
                break;
        }
        if (check != sum) { // Resort to locking all segments
            sum = 0;
            for (int i = 0; i < segments.length; ++i) {
                segments[i].lock();
            }
            for (int i = 0; i < segments.length; ++i) {
                sum += segments[i].count;
            }
            for (int i = 0; i < segments.length; ++i) {
                segments[i].unlock();
            }
        }
        if (sum > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        else
            return (int) sum;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     * <p/>
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code key.equals(k)},
     * then this method returns {@code v}; otherwise it returns
     * {@code null}.  (There can be at most one such mapping.)
     *
     * @throws NullPointerException if the specified key is null
     */
    @Override
    public V get(Object key) {
        Long rawKey = (Long) key;
        if (rawKey.longValue() == EMPTY_SLOT) {
            throw new UnsupportedOperationException("map dost not support 0 key!");
        }
        int hash = hash(rawKey);
        return segmentFor(hash).get((Long) key, hash);
    }

    /**
     * Tests if the specified object is a key in this table.
     *
     * @param key possible key
     *
     * @return <tt>true</tt> if and only if the specified object
     *         is a key in this table, as determined by the
     *         <tt>equals</tt> method; <tt>false</tt> otherwise.
     * @throws NullPointerException if the specified key is null
     */
    @Override
    public boolean containsKey(Object key) {
        Long rawKey = (Long) key;
        if (rawKey.longValue() == EMPTY_SLOT) {
            throw new UnsupportedOperationException("map dost not support 0 key!");
        }
        int hash = hash(rawKey);
        return segmentFor(hash).containsKey(rawKey, hash);
    }

    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value. Note: This method requires a full internal
     * traversal of the hash table, and so is much slower than
     * method <tt>containsKey</tt>.
     *
     * @param value value whose presence in this map is to be tested
     *
     * @return <tt>true</tt> if this map maps one or more keys to the
     *         specified value
     * @throws NullPointerException if the specified value is null
     */
    @Override
    public boolean containsValue(Object value) {
        if (value == null)
            throw new NullPointerException();

        // See explanation of modCount use above

        final Segment<V>[] segments = this.segments;
        int[] mc = new int[segments.length];

        // Try a few times without locking
        for (int k = 0; k < RETRIES_BEFORE_LOCK; ++k) {
            int sum = 0;
            int mcsum = 0;
            for (int i = 0; i < segments.length; ++i) {
                int c = segments[i].count;
                mcsum += mc[i] = segments[i].modCount;
                if (segments[i].containsValue((V) value))
                    return true;
            }
            boolean cleanSweep = true;
            if (mcsum != 0) {
                for (int i = 0; i < segments.length; ++i) {
                    int c = segments[i].count;
                    if (mc[i] != segments[i].modCount) {
                        cleanSweep = false;
                        break;
                    }
                }
            }
            if (cleanSweep)
                return false;
        }
        // Resort to locking all segments
        for (int i = 0; i < segments.length; ++i) {
            segments[i].lock();
        }
        boolean found = false;
        try {
            for (int i = 0; i < segments.length; ++i) {
                if (segments[i].containsValue((V) value)) {
                    found = true;
                    break;
                }
            }
        } finally {
            for (int i = 0; i < segments.length; ++i) {
                segments[i].unlock();
            }
        }
        return found;
    }

    /**
     * Maps the specified key to the specified value in this table.
     * Neither the key nor the value can be null.
     * <p/>
     * <p> The value can be retrieved by calling the <tt>get</tt> method
     * with a key that is equal to the original key.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     *
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>
     * @throws NullPointerException if the specified key or value is null
     */
    @Override
    public V put(Long key, V value) {
        if (value == null)
            throw new NullPointerException();
        if (key.longValue() == EMPTY_SLOT) {
            throw new UnsupportedOperationException("map dost not support 0 key!");
        }
        int hash = hash(key);
        return segmentFor(hash).put(key, hash, value);
    }


    private static int hash(long v) {
        int h = (int) (v ^ (v >>> 32));
        // Spread bits to regularize both segment and index locations,
        // using variant of single-word Wang/Jenkins hash.
        h += (h << 15) ^ 0xffffcd7d;
        h ^= (h >>> 10);
        h += (h << 3);
        h ^= (h >>> 6);
        h += (h << 2) + (h << 14);
        return h ^ (h >>> 16);
    }

    /**
     * Removes the key (and its corresponding value) from this map.
     * This method does nothing if the key is not in the map.
     *
     * @param key the key that needs to be removed
     *
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>
     * @throws NullPointerException if the specified key is null
     */
    @Override
    public V remove(Object key) {
        Long rawKey = (Long) key;
        if (rawKey.longValue() == EMPTY_SLOT) {
            throw new UnsupportedOperationException("map dost not support 0 key!");
        }
        int hash = hash(rawKey);
        return segmentFor(hash).remove(rawKey, hash, null);
    }

    /**
     * {@inheritDoc}
     *
     * @throws NullPointerException if the specified key is null
     */
    public boolean remove(Long key, V value) {
        if (key.longValue() == EMPTY_SLOT) {
            throw new UnsupportedOperationException("map dost not support 0 key!");
        }
        int hash = hash(key);
        return segmentFor(hash).remove(key, hash, value) != null;
    }


    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        for (int i = 0; i < segments.length; ++i) {
            segments[i].clear();
        }
    }

    @Override
    public Set<Map.Entry<Long, V>> entrySet() {
        throw new UnsupportedOperationException();
        //        Set<Map.Entry<Long, V>> es = entrySet;
        //        return (es != null) ? es : (entrySet = new EntrySet());
    }

    @Override
    public Set<Long> keySet() {
        return null; // TODO
    }

    @Override
    public Collection<V> values() {
        return new Values();
    }

    @Override
    public void putAll(Map<? extends Long, ? extends V> m) {
        for (Map.Entry<? extends Long, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    /**
     * 每次调用需要new一次这个对象
     */
    final class ValueIterator implements Iterator<V> {
        int currentSegmentIndex = 0;
        int currentIndex = 0;
        int nextSegmentIndex = 0;
        int nextIndex = 0;
        HashEntry<V>[] entries = new HashEntry[segments.length];

        ValueIterator() {
            for (int i = 0; i < segments.length; i++) {
                segments[i].lock();
                try {
                    //copy,deep copy更好,但是考虑到数据两比较大，不可取
                    entries[i] = segments[i].table;
                } finally {
                    segments[i].unlock();
                }
            }
            //默认找一下，做标记
            hasNext();
        }


        @Override
        public boolean hasNext() {
            //循环，一直往下找
            for (int i = currentSegmentIndex; i < segments.length; i++) {
                for (int j = currentIndex; j < entries[i].getSize(); j++) {
                    //read lock
                    segments[currentSegmentIndex].lock();
                    try {
                        if (entries[i].getKey(j) != EMPTY_SLOT) {
                            nextSegmentIndex = i;
                            nextIndex = j;
                            return true;
                        }
                    } finally {
                        segments[currentSegmentIndex].unlock();
                    }
                }
                currentIndex = 0;
            }
            return false;
        }

        @Override
        public V next() {
            currentSegmentIndex = nextSegmentIndex;
            currentIndex = nextIndex;
            V value = entries[currentSegmentIndex].getValue(currentIndex);
            currentIndex++;
            if (currentIndex >= entries[currentSegmentIndex].getSize()) {
                currentSegmentIndex++;
                currentIndex = 0;
            }
            return value;
        }

        @Override
        public void remove() {
            //暂时不支持
            throw new UnsupportedOperationException();
        }
    }

    final class Values extends AbstractCollection<V> {
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        public int size() {
            return LongReferenceConcurrentHashMap.this.size();
        }

        public boolean contains(Object o) {
            return LongReferenceConcurrentHashMap.this.containsValue(o);
        }

        public void clear() {
            LongReferenceConcurrentHashMap.this.clear();
        }
    }

}
