package cn.lichengwu.utils.collection;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * ConcurrentHashSet
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-08-06 3:25 PM
 */
public class ConcurrentHashSetTest extends AbstractBenchmark {

    ConcurrentHashSet cset;
    Set hset;
    private static final int SIZE = 1000000;

    @Before
    public void setUp() throws Exception {
        cset = new ConcurrentHashSet();
        hset = new HashSet();
    }

    @Test @BenchmarkOptions(benchmarkRounds = 10, concurrency = 1)
    public void testConcurrentHashSetAdd() {
        for (int i = 0; i < SIZE; i++) {
            cset.add(i);
        }
    }

    @Test @BenchmarkOptions(benchmarkRounds = 10, concurrency = 1)
    public void testHashSetAdd() {
        for (int i = 0; i < SIZE; i++) {
            hset.add(i);
        }
    }
}
