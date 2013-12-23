package cn.lichengwu.utils.sort;

import java.util.Comparator;

/**
 * 插入排序：
 * <p/>
 * 上界:O(N^2)
 * <p/>
 * 下界:O(N)
 *
 * @author 佐井
 * @version 1.0
 * @created 2013-12-23 11:55 PM
 */
public class InsertionSort<T> extends AbstractSort<T> {


    @Override
    protected void innerSort(T[] arr, Comparator<T> comparator) {
        //插入的正确位置
        int slot;
        //从第二个元素开始，把后面的元素插入到正确的位置
        for (int i = 1; i < arr.length; i++) {

            //要插入的元素
            T tmp = arr[i];

            //i向前寻找位置
            for (slot = i; slot > 0 && compare(tmp, arr[slot - 1], comparator) < 0; slot--) {
                //让出位置
                arr[slot] = arr[slot - 1];
            }
            //插入
            arr[slot] = tmp;
        }
    }
}
