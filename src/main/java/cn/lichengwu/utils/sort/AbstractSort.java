package cn.lichengwu.utils.sort;

import java.util.Comparator;

/**
 * 排序抽象
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-12-23 11:48 PM
 */
public abstract class AbstractSort<T> implements Sort<T> {

    @SuppressWarnings("unchecked")
    protected int compare(T t1, T t2, Comparator<T> comparator) {
        return comparator != null ? comparator.compare(t1, t2) : ((Comparable) t1).compareTo(t2);
    }


    @Override
    public void doSort(T[] arr) {
        doSort(arr, null);
    }

    @Override
    public void doSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || arr.length == 0) {
            return;
        }
        innerSort(arr, comparator);
    }

    protected abstract void innerSort(T[] arr, Comparator<T> comparator);
}
