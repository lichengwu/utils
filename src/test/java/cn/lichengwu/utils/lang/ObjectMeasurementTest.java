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
        System.out.println(ObjectMeasurement.sizeOf(new Object()));
    }
}
