package cn.lichengwu.utils.sort;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 排序抽象
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-12-23 11:48 PM
 */
public abstract class AbstractSort<T> implements Sort<T> {

    AtomicLong compareCounter = new AtomicLong(0);
    AtomicLong assignCounter = new AtomicLong(0);

    @SuppressWarnings("unchecked")
    protected int compare(T t1, T t2, Comparator<T> comparator) {
        compareCounter.incrementAndGet();
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
        System.out.println(this.getClass().getSimpleName() + ":");
        long begin = System.currentTimeMillis();
        innerSort(arr, comparator);
        System.out.println("\ttime used:\t\t" + (System.currentTimeMillis() - begin) + "ms");
        System.out.println("\tcompare times:\t" + compareCounter.get());
        System.out.println("\tmove times:\t\t" + assignCounter.get());
        compareCounter.set(0L);
        assignCounter.set(0L);
    }

    protected T assign(T from) {
        assignCounter.incrementAndGet();
        return from;
    }

    protected abstract void innerSort(T[] arr, Comparator<T> comparator);
}
