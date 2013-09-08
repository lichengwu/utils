package cn.lichengwu.utils.collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author 佐井
 * @version 1.0
 * @created 2013-09-06 11:24 PM
 */
public class LongReferenceConcurrentHashMapTest {

    int size = 1000000;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test(){
        Map<Long,String> map = new LongReferenceConcurrentHashMap<String>();
        for(Integer i =0;i<size;i++){
            map.put(i.longValue(),"sss");
        }
        System.out.println(map.size());
    }
}
