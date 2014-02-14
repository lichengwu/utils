package cn.lichengwu.utils.sort;

import java.util.Comparator;

/**
 * 归并排序：
 * <p/>
 * 上界:O(N log N)
 * <p/>
 * 下界:O(N)
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-12-28 11:34 AM
 */
public class MergeSort<T> extends AbstractSort<T> {
    @Override
    protected void innerSort(T[] arr, Comparator<T> comparator) {
        T[] tmp = arr.clone();
        mergeSort(arr, tmp, 0, arr.length - 1, comparator);
    }


    private void mergeSort(T[] arr, T[] tmp, int left, int right, Comparator<T> comparator) {
        if (left < right) {
            int mid = (right + left) / 2;
            mergeSort(arr, tmp, left, mid, comparator);
            mergeSort(arr, tmp, mid + 1, right, comparator);
            merge(arr, tmp, left, mid+1, right, comparator);
        }
    }

    /**
     * 合并两个已经排序好的数组
     *
     * @param arr
     * @param tmp
     * @param left
     * @param right
     * @param rightEnd
     * @param comparator
     */
    private void merge(T[] arr, T[] tmp, int left, int right, int rightEnd, Comparator<T> comparator) {

        int tmpIndex = left;
        int leftEnd = right - 1;
        int nElements = rightEnd - left + 1;

        //主循环
        while (left <= leftEnd && right <= rightEnd) {
            if (compare(arr[left], arr[right], comparator) < 0) {
                tmp[tmpIndex++] = assign(arr[left++]);
            } else {
                tmp[tmpIndex++] = assign(arr[right++]);
            }
        }
        //如果左边有剩余，拷贝
        while (left <= leftEnd) {
            tmp[tmpIndex++] = assign(arr[left++]);
        }
        //如果右边有剩余，拷贝
        while (right <= rightEnd) {
            tmp[tmpIndex++] = assign(arr[right++]);
        }
        //copy到原数组
        for (int i = 0; i < nElements; i++, rightEnd--) {
            arr[rightEnd] = assign(tmp[rightEnd]);
        }

    }

}
