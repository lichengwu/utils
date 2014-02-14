package cn.lichengwu.utils.collection;

import java.security.PublicKey;
import java.util.Comparator;

/**
 * 二叉堆实现
 *
 * @author 佐井
 * @version 1.0
 * @created 2013-12-15 1:03 PM
 */
public class BinaryHeap<T> {

    private static final int DEFAULT_SIZE = 16;

    private Object[] elements;

    private int size;

    private Comparator<T> comparator;

    public BinaryHeap() {
        this(DEFAULT_SIZE, null);
    }

    public BinaryHeap(int size) {
        this(size, null);
    }

    public BinaryHeap(Comparator<T> comparator) {
        this(DEFAULT_SIZE, comparator);
    }

    public BinaryHeap(int size, Comparator<T> comparator) {
        elements = new Object[size];
        this.comparator = comparator;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Object[] toArray() {
        if (elements == null || elements.length == 0) {
            return new Object[]{};
        }
        Object[] arr = new Object[elements.length];
        System.arraycopy(elements, 0, arr, 0, elements.length);
        return arr;
    }

    public boolean offer(T t) {
        if (size == elements.length - 1) {
            ensureCapacity(elements.length * 2 + 1);
        }

        int hold = ++size;

        while (hold > 1 && compare(t, elements[hold / 2]) < 0) {
            elements[hold] = elements[hold / 2];
            hold /= 2;
        }
        elements[hold] = t;
        return true;
    }


    private void percolateDown(int hold) {
        int child;
        T tmp = (T) elements[hold];
        while (hold * 2 < size) {
            child = hold * 2;
            if (child != size && compare(elements[child + 1], elements[child]) < 0) {
                child++;
            }
            if (compare(elements[child], tmp) < 0) {
                elements[hold] = elements[child];
                hold = child;
            } else {
                break;
            }
        }
        elements[hold] = tmp;
    }


    public void clear() {
        size = 0;
    }

    public T poll() {
        if (isEmpty()) {
            return null;
        }
        T top = (T) elements[1];
        elements[1] = elements[size--];
        percolateDown(1);
        return top;
    }


    private void ensureCapacity(int newSize) {
        if (newSize < 0) {
            throw new IllegalArgumentException("inner array size must greater than 0:" + newSize);
        }
        Object[] newArr = new Object[newSize];
        System.arraycopy(elements, 0, newArr, 0, elements.length);
        elements = newArr;
    }

    @SuppressWarnings("unchecked")
    private int compare(Object k1, Object k2) {
        return comparator == null ? ((Comparable<? super T>) k1).compareTo((T) k2) : comparator.compare((T) k1, (T) k2);
    }
}
