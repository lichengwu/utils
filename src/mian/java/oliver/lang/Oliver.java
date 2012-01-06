/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package oliver.lang;

import java.io.Serializable;

/**
 * Oliver
 * 
 * @author lichengwu
 * @created Aug 7, 2011
 * 
 * @version 1.0
 */
public class Oliver implements Serializable {

    private static final long serialVersionUID = -5878048307879683975L;
    private String name;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    private int age;

}
