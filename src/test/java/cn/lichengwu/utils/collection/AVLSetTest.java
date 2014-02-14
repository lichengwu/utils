package cn.lichengwu.utils.collection;

import cn.lichengwu.utils.lucene.index.IndexReaderPool;
import com.sun.swing.internal.plaf.synth.resources.synth_sv;
import org.junit.Test;

import java.util.Set;

/**
 *
 * @author 佐井
 * @version 1.0
 * @created 2013-12-07 9:51 PM
 */
public class AVLSetTest {

    @Test
    public void test(){
        Set<Integer> set = new AVLSet<Integer>();
        for(int i=10;i>0;i--){
            set.add(+i);
        }
        for (Integer s : set) {
            System.out.println(s);
        }
    }
}
