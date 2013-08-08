package cn.lichengwu.utils.lang;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lichengwu
 * @version 1.0
 * @created 2013-08-08 2:03 PM
 */
public class ObjectMeasurementTest {

    public static void main(String[] args) throws Exception {
        Map<Integer, Object> object = new HashMap<Integer, Object>();
        Integer i = 0;
        object.put(11, i);
        //        int [] b = {1,4,2};
        String str = "";
        System.out.println("current size: " + ObjectMeasurement.sizeOf(str));
        System.out.println("deep size: " + ObjectMeasurement.deepSizeOf(str));
    }
}
