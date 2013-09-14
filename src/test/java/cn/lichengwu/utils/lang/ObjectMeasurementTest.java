package cn.lichengwu.utils.lang;

import org.junit.Ignore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lichengwu
 * @version 1.0
 * @created 2013-08-08 2:03 PM
 */
@Ignore
public class ObjectMeasurementTest {

    /**
     * -javaagent:/home/zuojing/workspace/java/utils/target/utils-1.3.0-SNAPSHOT.jar
     *
     * @param args
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        //        Map<Integer, Object> object = new HashMap<Integer, Object>();
        //        Integer i = 0;
        //        object.put(11, i);
        //        //        int [] b = {1,4,2};
        //        String str = "";
        //        BitSet set = new BitSet(24*60);
        //        set.set(0,356,true);
        Map<Long, Object> map = new ConcurrentHashMap<Long,Object>();
        for (Integer i = 0; i < 1000000; i++) {
            map.put(i.longValue(), Boolean.TRUE);
        }

        //        System.out.println("current size: " + ObjectMeasurement.sizeOf(map));
//                System.out.println("deep size: " + ObjectMeasurement.deepSizeOf(map));
        System.out.println(ObjectMeasurement.deepSizeOf(new HashEntry()));


    }

    public static final class HashEntry<V> {
        long key;
        int hash;
//        volatile V value;
        V[] next;

    }
}
