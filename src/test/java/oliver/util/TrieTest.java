package oliver.util;

import java.util.ArrayList;

import oliver.lang.Random;
import oliver.lang.RandomString;
import oliver.util.colloection.Trie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Trie数据结构测试
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-10-23 下午8:21
 */
public class TrieTest {

    private static final int N = 100000;

    private String[] array;

    @Before
    public void setUp() {
        array = new String[N];
        for (int i = 0; i < N; i++) {
            array[i] = RandomString.get(Random.rangeRandom(2, 30));
        }
    }

    @Test
    public void test() {
        Trie trie = new Trie();

        Assert.assertTrue(trie.add("oliver"));
        Assert.assertTrue(trie.contains("oliver"));
        Assert.assertTrue(trie.remove("oliver"));
        Assert.assertFalse(trie.contains("oliver"));
    }

    /**
     * 10W 长度2-30字符穿 平均耗时3s
     */
    @Test
    public void testTrie() {
        Trie trie = new Trie();
        for (String str : array) {
            trie.add(str);
        }

        for (String str : array) {
            Assert.assertTrue(trie.contains(str));
        }
        // Assert.assertTrue(trie.isEmpty());
        // Assert.assertEquals(0, trie.size());
    }

    /**
     * 10W 长度2-30字符穿 平均耗时90s
     *
     */
    @Test
    public void testArray() {
        ArrayList<String> arrayList = new ArrayList<String>(array.length);
        for (String str : array) {
            arrayList.add(str);
        }

        for (int i = array.length - 1; i >= 0; i--) {
            Assert.assertTrue(arrayList.contains(array[i]));
        }
    }
}
