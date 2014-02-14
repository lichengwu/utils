package cn.lichengwu.utils.sort;

import java.util.Comparator;

/**
 * 希尔排序：
 * * <p/>
 * 上界:O(N^2)
 * <p/>
 * 下界:O(N)
 * <p/>
 * 插入排序的改进版
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-12-25 10:54 PM
 */
public class ShellSort<T> extends AbstractSort<T> {
    @Override
    protected void innerSort(T[] arr, Comparator<T> comparator) {
        int pointer;
        //逐渐缩小间隔距离
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //往后开始比较
            for (int i = gap; i < arr.length; i++) {
                T tmp = arr[i];
                //初始化指针
                pointer = i;
                //找到合适位置，插入
                for (; pointer >= gap && compare(tmp, arr[pointer - gap], comparator) < 0; pointer -= gap) {
                    arr[pointer] = assign(arr[pointer - gap]);
                }
                arr[pointer] = tmp;
            }
        }
    }
}
