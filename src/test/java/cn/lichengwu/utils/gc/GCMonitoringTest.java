package cn.lichengwu.utils.gc;

import junit.framework.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * test for {@linkplain GCMonitoring}
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-09-14 10:59 PM
 */
public class GCMonitoringTest {


    @Test
    public void test() {

        GCMonitoring.init();
        System.gc();
        for (int i = 0; i < 100; i++) {
            byte[] b = new byte[1024 * 1024];
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Assert.fail();
        }
    }
}
