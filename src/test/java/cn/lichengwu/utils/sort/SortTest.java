package cn.lichengwu.utils.sort;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * @author lichengwu
 * @version 1.0
 * @created 2013-12-24 12:20 AM
 */
public class SortTest {

    private Integer[] arr;

    private int size = 10000;

    private Random random;


    @Before
    public void setUp() throws Exception {
        random = new Random();
        arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < size / 4; i++) {
            int x1 = random.nextInt(size);
            int x2 = random.nextInt(size);
            Integer tmp = arr[x1];
            arr[x1] = arr[x2];
            arr[x2] = tmp;
        }
    }

    @Test
    public void testSort() throws Exception {
        Sort<Integer> sort = new InsertionSort<Integer>();
        sort.doSort(arr);
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(arr[i] == i);
        }

    }
}
