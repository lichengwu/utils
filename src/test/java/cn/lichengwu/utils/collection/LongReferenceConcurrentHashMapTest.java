package cn.lichengwu.utils.collection;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 佐井
 * @version 1.0
 * @created 2013-09-06 11:24 PM
 */
public class LongReferenceConcurrentHashMapTest {


    //    @Rule
    //    public TestRule benchmarkRun = new BenchmarkRule();

    private static Random rand = new Random();

    final static int SIZE = 5000000;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    //    @BenchmarkOptions(concurrency = 4)
    public void testLongMapPut() {
        long begin = System.currentTimeMillis();
        Map<Long, Object> longMap = new ConcurrentHashMap<>();
        for (long i = 1; i <= SIZE; i++) {
            longMap.put(i, Boolean.TRUE);
        }
        System.out.println(longMap.size());
        System.out.println("long map put cost:" + (System.currentTimeMillis() - begin) + "ms");
    }

    @Test
    public void testLongMapGet() {
        Map<Long, Long> longMap = new LongReferenceConcurrentHashMap();
        Map<Long, Long> concurrentMap = new HashMap<>();
        System.out.println("开始准备数据...");
        for (long i = 1; i <= SIZE; i++) {
            long kv = rand.nextLong();
            longMap.put(kv, kv);
            concurrentMap.put(kv, kv);
            Assert.assertFalse(longMap.isEmpty());
        }
        long begin = System.nanoTime();
        for (Map.Entry<Long, Long> entry : concurrentMap.entrySet()) {
            longMap.get(entry.getKey());
        }
        System.out.println("avg long map get cost:" + (System.nanoTime() - begin) / SIZE);
        Assert.assertEquals(SIZE, longMap.size());


    }


    @Test
    public void testVeracity() {
        Map<Long, Long> longMap = new LongReferenceConcurrentHashMap();
        Map<Long, Long> concurrentMap = new HashMap<>();
        System.out.println("开始准备数据...");
        for (long i = 1; i <= SIZE; i++) {
            long kv = rand.nextLong();
            longMap.put(kv, kv);
            concurrentMap.put(kv, kv);
        }
        System.out.println("准备数据完成...");
        int iCount = SIZE;
        for (Map.Entry<Long, Long> entry : concurrentMap.entrySet()) {
            Assert.assertFalse(longMap.isEmpty());
            Assert.assertTrue(longMap.containsKey(entry.getKey()));
            Assert.assertEquals(longMap.get(entry.getKey()), entry.getValue());
            Assert.assertEquals(longMap.remove(entry.getKey()), entry.getValue());
            Assert.assertEquals(--iCount, longMap.size());
        }
        Assert.assertTrue(longMap.isEmpty());

    }


    @Test
    public void testValueIterator() {
        Map<Long, Object> longMap = new LongReferenceConcurrentHashMap();
        Map<Long, Object> hashMap = new HashMap<>();
        int size = 100;
        for (long i = 1; i <= size; i++) {
            longMap.put(i, i);
            hashMap.put(i, i);
        }
        Set<Object> set = new HashSet<>();
        for(Object val : longMap.values()){
            set.add(val);
            System.out.println(val);
//            System.out.println(val);
        }
        Assert.assertEquals(set.size(),size);

//        Assert.assertEquals(SIZE, longMap.size());
//
//        for (Object value : longMap.values()) {
////            Assert.assertTrue(hashMap.containsValue(value));
//            Assert.assertTrue(hashMap.remove(value) != null);
//        }
//        System.out.println(hashMap);
//        Assert.assertTrue(hashMap.isEmpty());
//        longMap.clear();
//
//        Assert.assertTrue(longMap.isEmpty());


    }


    @Test
    @BenchmarkOptions(concurrency = 4)
    public void testConcurrentMapPut() {
        long begin = System.currentTimeMillis();
        Map<Long, Object> map = new ConcurrentHashMap<>(4, 0.75f, 4);
        for (long i = 1; i <= SIZE; i++) {
            map.put(i, Boolean.TRUE);
        }
        System.out.println("concurrent map put cost:" + (System.currentTimeMillis() - begin) + "ms");
    }

    @Test
    public void test() {
        LongReferenceConcurrentHashMap<String> map = new LongReferenceConcurrentHashMap();
        Map<Long, String> cMap = new ConcurrentHashMap<>();
        for (Integer i = 1; i < SIZE; i++) {
            long key = rand.nextLong();
            map.put(key, "sss" + i);
            cMap.put(key, "sss" + i);
        }

        Assert.assertEquals(map.size(), cMap.size());

        for (Map.Entry<Long, String> entry : cMap.entrySet()) {
            Assert.assertTrue(map.containsKey(entry.getKey()));
            Assert.assertEquals(entry.getValue(), map.get(entry.getKey()));
        }

        for (Map.Entry<Long, String> entry : cMap.entrySet()) {
            Assert.assertTrue(map.remove(entry.getKey()) != null);
        }

        System.out.println(map.size());

        Assert.assertTrue(map.isEmpty());

        //
        //
        //        System.out.println(map.size());
    }

    @Test
    public void testHash() throws InterruptedException {
        final long dif = Long.MAX_VALUE / 4;
        long begin = Long.MIN_VALUE;

        ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

        for (; ; ) {
            final long iBegin = begin;
            exec.submit(new Runnable() {
                @Override
                public void run() {
                    for (long i = iBegin; i < (iBegin + dif); i++) {
                        int hash = (int) (i ^ (i >>> 32));
                        if (hash == 0) {
                            System.out.println(i + ",hash=" + hash);
                        }
                    }
                }
            });
            if (begin == Long.MAX_VALUE) {
                break;
            }
            if (begin > (begin + dif)) {
                begin = Long.MAX_VALUE;
            } else {
                begin += dif;
            }
        }


        exec.awaitTermination(1, TimeUnit.DAYS);


    }
}
