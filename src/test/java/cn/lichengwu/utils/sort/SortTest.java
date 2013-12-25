package cn.lichengwu.utils.sort;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author lichengwu
 * @version 1.0
 * @created 2013-12-24 12:20 AM
 */
public class SortTest {

    private Integer[] arr;

    private int size = 10000;

    private static final Set<Sort> sortSet = new HashSet<Sort>();


    @Before
    public void setUp() throws Exception {
        Random random = new Random();
        arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < size * 2; i++) {
            int x1 = random.nextInt(size);
            int x2 = random.nextInt(size);
            Integer tmp = arr[x1];
            arr[x1] = arr[x2];
            arr[x2] = tmp;
        }
        sortSet.add(new InsertionSort<Integer>());
        sortSet.add(new ShellSort<Integer>());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSort() throws Exception {
        for (Sort<Integer> sort : sortSet) {
            Integer[] newArr = buildNewArray();
            sort.doSort(newArr);
            for (Integer i = 0; i < size; i++) {
                Assert.assertEquals(newArr[i], i);
            }
        }
    }

    private Integer[] buildNewArray() {
        Integer[] newArr = new Integer[arr.length];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        return newArr;
    }
}
