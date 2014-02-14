package cn.lichengwu.utils.collection;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author 佐井
 * @version 1.0
 * @created 2013-12-15 1:57 PM
 */
public class BinaryHeapTest {

    @Test
    public void test() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>();
        int size = 100000;
        for (int i = size; i >0; i--) {
            heap.offer(i);
        }

        Assert.assertTrue(size == heap.size());
        for (int i = 1; i <= size; i++) {
            Assert.assertEquals(Integer.valueOf(i), heap.poll());
        }

    }
}
