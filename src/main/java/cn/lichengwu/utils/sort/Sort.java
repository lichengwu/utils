package cn.lichengwu.utils.sort;

import java.util.Comparator;

/**
 * 排序接口
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-12-23 11:46 PM
 */
public interface Sort<T> {

    void doSort(T[] arr);

    void doSort(T[] arr, Comparator<T> comparator);

}
