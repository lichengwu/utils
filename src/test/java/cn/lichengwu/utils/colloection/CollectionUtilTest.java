package cn.lichengwu.utils.colloection;

import cn.lichengwu.utils.common.bean.Person;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@linkplain CollectionUtil} test
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-07-13 4:39 PM
 */
public class CollectionUtilTest {

    private static final Logger log = LoggerFactory.getLogger(CollectionUtilTest.class);

    List<Person> personList = null;

    Map<Integer, Person> personMap = null;

    private static final int SIZE = 10;


    @Before
    public void setUp() {
        personList = new ArrayList<Person>(SIZE);
        personMap = new HashMap<Integer, Person>();
        for (int i = 0; i < SIZE; i++) {
            Person person = new Person(i, "name" + i);
            personList.add(person);
            personMap.put(i, person);
        }
    }


    @Test
    public void testGenerateString() throws Exception {
        String str = CollectionUtil.generateString(personList, "age", ",");
        Assert.assertNotNull(str);
        log.debug(str);
    }

    @Test
    public void testToString() throws Exception {
        String str = CollectionUtil.toString(personList, ",");
        Assert.assertNotNull(str);
        log.debug(str);
    }

    @Test
    public void testDefaultCollection() throws Exception {
        Person person = new Person(0, "default");
        List<Person> list = new ArrayList<Person>(SIZE);
        CollectionUtil.defaultCollection(list, SIZE, person);
        Assert.assertEquals(list.size(), SIZE);
        for (Person p : list) {
            Assert.assertEquals(p, person);
        }
    }

    @Test
    public void testSubMap() throws Exception {
        List<Integer> ageList = CollectionUtil.generatePropertyList(personList, "age");
        int size = SIZE / 2;
        List<Integer> subList = ageList.subList(0, size);
        Assert.assertEquals(size, subList.size());
        Map<Integer, Person> subMap = CollectionUtil.subMap(personMap, subList);
        Assert.assertEquals(subMap.size(), size);
    }

    @Test
    public void testGeneratePropertyList() throws Exception {
        List<String> nameList = CollectionUtil.generatePropertyList(personList, "name");
        Assert.assertEquals(nameList.size(), personList.size());
        log.debug(CollectionUtil.toString(nameList, " | "));
    }
}
