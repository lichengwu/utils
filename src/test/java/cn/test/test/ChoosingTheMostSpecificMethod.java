/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.test.test;

/**
 * 
 * @author lichengwu
 * @created 2012-1-6
 * 
 * @version 1.0
 */
public class ChoosingTheMostSpecificMethod {

	public static void method(Object obj) {
		System.out.println("method with param type - Object");
	}

	public static void method(String obj) {
		System.out.println("method with param type - String");
	}
	public static void method(Integer obj) {
		System.out.println("method with param type - Integer");
	}

	public static void main(String[] args) {
//		method(null);
	}

}
