/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.lichengwu.lang;

/**
 * Template of Enumeration for general usage.
 *
 * @author lichengwu
 * @created 2012-6-9
 *
 * @version 1.0
 */
public enum EnumTemplate {
	
	/**
	 * TemplateOne
	 */
	TemplateOne("TemplateOne", 1),

	/**
	 * TemplateTwo
	 */
	TemplateTwo("TemplateTwo", 2),

	/**
	 * TemplateThree
	 */
	TemplateThree("TemplateThree", 3);

    private String name;

    private int index;

    EnumTemplate(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (EnumTemplate template : EnumTemplate.values()) {
            if (template.getIndex() == index) {
                return template.name;
            }
        }
        return null;
    }

    /**
     * get the {@link EnumTemplate} index by name
     * 
     * @author lichengwu
     * @created 2012-6-9
     *
     * @param name
     * @return {@link EnumTemplate} index
     */
    public static Integer getIndex(String name) {
        for (EnumTemplate template : EnumTemplate.values()) {
            if (template.getName().equals(name)) {
                return template.index;
            }
        }
        return null;
    }

    /**
     * get the {@link EnumTemplate} by index
     * 
     * @author lichengwu
     * @created 2012-6-9
     *
     * @param index
     * @return the {@link EnumTemplate} which  index is match param, or null
     */
    public static EnumTemplate getEnumTemplate(int index) {
        for (EnumTemplate template : EnumTemplate.values()) {
            if (template.getIndex() == index) {
                return template;
            }
        }
        return null;
    }

    /**
     * get the {@link EnumTemplate}'s index
     * 
     * @author lichengwu
     * @created 2012-6-9
     *
     * @return the {@link EnumTemplate}'s index
     */
    public int getIndex() {
        return index;
    }

    /**
     * get the {@link EnumTemplate}'s name
     * 
     * @author lichengwu
     * @created 2012-6-9
     *
     * @return the {@link EnumTemplate}'s name
     */
    public String getName() {
        return name;
    }
}
