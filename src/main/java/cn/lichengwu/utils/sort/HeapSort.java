package cn.lichengwu.utils.sort;

import java.util.Comparator;

/**
 * 堆排序：
 * <p/>
 * 上界:O(N log N)
 * <p/>
 * 下界:O(N)
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-12-28 11:36 AM
 */
public class HeapSort<T> extends AbstractSort<T> {


    @Override
    protected void innerSort(T[] arr, Comparator<T> comparator) {
        //构造堆
        for (int i = arr.length / 2; i >= 0; i--) {
            precDown(arr, i, arr.length, comparator);
        }
        //把堆顶元素放到数组头
        for (int i = arr.length - 1; i > 0; i--) {
            //交换数据
            T tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;
            //下率tmp
            precDown(arr, 0, i, comparator);
        }
    }


    /**
     * 下滤
     *
     * @param arr
     * @param hole  要下滤的元素
     * @param limit 边界
     */
    private void precDown(T[] arr, int hole, int limit, Comparator<T> comparator) {

        //临时需要交换的数据
        T tmp;
        //存储左儿子或者右儿子
        int child;
        //左儿子
        int leftChild;
        for (tmp = arr[hole]; (leftChild = hole * 2 + 1) < limit; hole = child) {
            //假设是左儿子
            child = leftChild;
            //如果右儿子比左儿子小，那么选择右儿子
            if (child != limit - 1 && compare(arr[child], arr[child + 1], comparator) < 0) {
                child++;
            }
            //如果能够下沉，交换并继续
            if (compare(tmp, arr[child], comparator) < 0) {
                arr[hole] = assign(arr[child]);
            } else {
                //完成，返回
                break;
            }
        }
        arr[hole] = assign(tmp);
    }
}
