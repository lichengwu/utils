package cn.lichengwu.utils.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * {@linkplain cn.lichengwu.utils.collection.FastExtremeValueAccessStack} test
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-07-13 7:44 PM
 */
public class FastExtremeValueAccessStackTest {

    private List<Integer> randomList;

    private List<Integer> sortedList;

    private static final int SIZE = 2000;

    private Random random;

    @Before
    public void setUp() {
        random = new Random(47);
        randomList = new ArrayList<Integer>(SIZE);
        sortedList = new ArrayList<Integer>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            int value = random.nextInt();
            randomList.add(value);
            sortedList.add(value);
        }

        Collections.sort(sortedList);
    }

    @Test
    public void test() {
        FastExtremeValueAccessStack<Integer> stack = new FastExtremeValueAccessStack<Integer>();
        for(Integer value : randomList){
            stack.push(value);
        }

        Assert.assertEquals(stack.max(),sortedList.get(sortedList.size()-1));
        Assert.assertEquals(stack.min(),sortedList.get(0));

    }
}
