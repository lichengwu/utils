package cn.lichengwu.utils.cache;

import cn.lichengwu.utils.common.bean.Person;
import junit.framework.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * test for {@linkplain HeapCache}
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-07-13 3:59 PM
 */
public class HeapCacheTest {

    private static final int SIZE = 100;


    @Test
    public void test() throws InterruptedException {

        HeapCache cache = new HeapCache();
        cache.init();

        for (int i = 0; i < SIZE; i++) {
            if (i % 2 == 0) {
                cache.put("cache" + i, new Person(i, "name" + i));
            } else {
                cache.put("cache" + i, new Person(i, "name" + i), 1L);
            }
        }

        TimeUnit.SECONDS.sleep(1);

        Assert.assertEquals(cache.size(), SIZE);

        int iCount = 0;

        for (int i = 0; i < SIZE; i++) {
            Serializable value = cache.get("cache" + i);
            if (i % 2 == 0) {
                Assert.assertNotNull(value);
            } else {
                Assert.assertNull(value);
                iCount++;
            }
        }

        Assert.assertEquals(iCount, cache.size());

        cache.clear();

        Assert.assertEquals(0, cache.size());

    }
}
