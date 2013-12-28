package cn.lichengwu.utils.sort;

import java.util.Comparator;

/**
 * 快速排序：
 * 最坏：N^2
 * 平均：N long N
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-12-28 7:16 PM
 */
public class QuickSort<T> extends AbstractSort<T> {


    /**
     * 优化：大于阈值用插入排序
     */
    private static final int CUTOFF = 7;

    @Override
    protected void innerSort(T[] arr, Comparator<T> comparator) {
        quickSort(arr, 0, arr.length - 1, comparator);
    }

    private void quickSort(T[] arr, int left, int right, Comparator<T> comparator) {
        if (left + CUTOFF <= right) {
            T pivot = mid3Optimized(arr, left, right, comparator);
            int i = left, j = right - 1;
            for (; ; ) {
                while (compare(arr[i], pivot, comparator) < 0) {
                    i++;
                }

                while (compare(pivot, arr[j], comparator) < 0) {
                    j--;
                }
                if (i < j) {
                    T tmp = arr[i];
                    arr[i] = assign(arr[j]);
                    arr[j] = assign(tmp);
                } else {
                    break;
                }
            }
            T tmp = arr[right];
            arr[right] = assign(arr[i]);
            arr[i] = assign(tmp);
            quickSort(arr, left, i - 1, comparator);
            quickSort(arr, i + 1, right, comparator);

        } else {
            insertSort(arr, left, right, comparator);
        }


    }

    /**
     * 在lef，right和(left+right)/2中选择中间的值
     *
     * @param arr
     * @param left
     * @param right
     * @param comparator
     *
     * @return
     */
    @SuppressWarnings("unused")
    private T mid3(T[] arr, int left, int right, Comparator<T> comparator) {
        int center = (left + right) / 2;
        //比较并交换
        if (compare(arr[left], arr[center], comparator) > 0) {
            T tmp = arr[left];
            arr[left] = assign(arr[center]);
            arr[center] = assign(tmp);
        }
        if (compare(arr[left], arr[right], comparator) > 0) {
            T tmp = arr[right];
            arr[right] = assign(arr[left]);
            arr[left] = assign(tmp);
        }
        if (compare(arr[center], arr[right], comparator) > 0) {
            T tmp = arr[right];
            arr[right] = assign(arr[center]);
            arr[center] = assign(tmp);
        }

        //中值放在最后
        T tmp = arr[center];
        arr[center] = assign(arr[right]);
        arr[right] = assign(tmp);
        return arr[right];
    }


    /**
     * 在lef，right和(left+right)/2中选择中间的值
     *
     * @param arr
     * @param left
     * @param right
     * @param comparator
     *
     * @return
     */
    private T mid3Optimized(T[] arr, int left, int right, Comparator<T> comparator) {
        int center = (left + right) / 2;
        //比较并交换
        if (compare(arr[left], arr[center], comparator) > 0) {
            T tmp = arr[left];
            arr[left] = assign(arr[center]);
            arr[center] = assign(tmp);
        }
        if (compare(arr[left], arr[right], comparator) > 0) {
            T tmp = arr[right];
            arr[right] = assign(arr[left]);
            arr[left] = assign(tmp);
        }
        if (compare(arr[center], arr[right], comparator) > 0) {
            return arr[right];
        }

        //中值放在最后
        T tmp = arr[center];
        arr[center] = assign(arr[right]);
        arr[right] = assign(tmp);
        return arr[right];
    }

    private void insertSort(T[] arr, int left, int right, Comparator<T> comparator) {
        //插入的正确位置
        int slot;
        //从第二个元素开始，把后面的元素插入到正确的位置
        for (int i = left + 1; i <= right; i++) {

            //要插入的元素
            T tmp = arr[i];

            //i向前寻找位置
            for (slot = i; slot > 0 && compare(tmp, arr[slot - 1], comparator) < 0; slot--) {
                //让出位置
                arr[slot] = assign(arr[slot - 1]);
            }
            //插入
            arr[slot] = assign(tmp);
        }
    }

}
