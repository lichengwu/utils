package cn.lichengwu.utils.common.bean;

import java.io.Serializable;

/**
 * Person bean
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-07-13 4:42 PM
 */
public class Person implements Serializable {

    private static final long serialVersionUID = -680744361069548623L;
    private int age;

    private String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
