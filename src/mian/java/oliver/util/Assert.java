/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package oliver.util;

import java.util.Collection;
import java.util.Map;

import oliver.util.string.StringUtil;

/**
 * 用于断定表达式或对象是否成立，如果不成立，抛出
 * <code>{@link java.lang.IllegalArgumentException}<code>异常。
 * 
 * @author lichengwu
 * @created 2011-12-14
 * 
 * @version 1.0
 */
public final class Assert {
	/**
	 * 断言一个布尔表达式为真
	 * 
	 * @author lichengwu
	 * @created 2011-12-14
	 * 
	 * @param expression
	 * @param message
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言一个布尔表达式为真
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param expression
	 */
	public static void isTure(boolean expression) {
		isTrue(expression, "[断言失败] - 表达式必须为真");
	}

	/**
	 * 断言一个对象为空
	 * 
	 * @author lichengwu
	 * @created 2011-12-14
	 * 
	 * @param object
	 * @param message
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言一个对象为空
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param object
	 */
	public static void isNull(Object object) {
		isNull(object, "[断言失败] - 对象必须为空");
	}

	/**
	 * 断言一个对象不为空
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param object
	 * @param message
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言一个对象不为空
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param message
	 */
	public static void notNull(Object object) {
		if (object == null) {
			throw new IllegalArgumentException("[断言失败] - 对象不能为空");
		}
	}

	/**
	 * 断言一个字符串不是空串,即如果str=null或者str=""或者str="   "则断言失败
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param str
	 * @param message
	 */
	public static void notBlank(String str, String message) {
		if (StringUtil.isBlank(str)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言一个字符串不是空串,即如果str=null或者str=""则断言失败
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param str
	 */
	public static void notBlank(String str) {
		if (StringUtil.isBlank(str)) {
			throw new IllegalArgumentException("[断言失败] - 字符串必须包含内容");
		}
	}

	/**
	 * 断言给定字符串不包含给定子串 注意：这个断言会对textToSearch 进行trim操作
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param textToSearch
	 * @param subStr
	 * @param message
	 */
	public static void notContain(String textToSearch, String subStr,
			String message) {
		if (!StringUtil.isBlank(textToSearch)
				&& textToSearch.indexOf(subStr) != -1) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言给定字符串不包含给定子串 注意：这个断言会对textToSearch 进行trim操作
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param textToSearch
	 * @param subStr
	 */
	public static void notContain(String textToSearch, String subStr) {
		if (!StringUtil.isBlank(textToSearch)
				&& textToSearch.indexOf(subStr) != -1) {
			throw new IllegalArgumentException("[断言失败] - \"" + textToSearch
					+ "\" 不能包含\"" + subStr + "\"");
		}
	}

	/**
	 * 断言数组不能为空,即array==null或者array.length==0断言失败
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param array
	 * @param message
	 */
	public static void notEmpty(Object[] array, String message) {
		if (array == null || array.length == 0) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言数组不能为空,即array==null或者array.length==0断言失败
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param array
	 */
	public static void notEmpty(Object[] array) {
		if (array == null || array.length == 0) {
			throw new IllegalArgumentException("[断言失败] - 数组至少存在一个元素");
		}
	}

	/**
	 * 断言数组不包含null元素
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param array
	 * @param message
	 */
	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (Object obj : array) {
				if (obj == null) {
					throw new IllegalArgumentException(message);
				}
			}
		}
	}

	/**
	 * 断言数组不包含null元素
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param array
	 * @param message
	 */
	public static void noNullElements(Object[] array) {
		if (array != null) {
			for (Object obj : array) {
				if (obj == null) {
					throw new IllegalArgumentException("[断言失败] - 数组中不能包含null");
				}
			}
		}
	}

	/**
	 * 断言集合不为空
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param collection
	 * @param message
	 */
	public static void notEmpty(Collection<?> collection, String message) {
		if (collection == null || collection.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言集合不为空
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param collection
	 * @param message
	 */
	public static void notEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			throw new IllegalArgumentException("[断言失败] - 集合不能为空");
		}
	}

	/**
	 * 断言Map不为空
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param collection
	 * @param message
	 */
	public static void notEmpty(Map<?, ?> map, String message) {
		if (map == null || map.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言Map不为空
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param collection
	 * @param message
	 */
	public static void notEmpty(Map<?, ?> map) {
		if (map == null || map.isEmpty()) {
			throw new IllegalArgumentException("[断言失败] - Map不能为空");
		}
	}

	/**
	 * 断言给定对象是某个class的实例
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param type
	 * @param obj
	 * @param message
	 */
	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "类型不能为空");
		if (!type.isInstance(obj)) {
			throw new IllegalArgumentException(message + "对象的类型 ["
					+ (obj != null ? obj.getClass().getName() : "null")
					+ "] 必须是 " + type);
		}
	}

	/**
	 * 断言给定对象是某个class的实例
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param type
	 * @param obj
	 */
	public static void isInstanceOf(Class<?> clazz, Object obj) {
		isInstanceOf(clazz, obj, "");
	}

}
