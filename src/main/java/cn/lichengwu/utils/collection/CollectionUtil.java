/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.lichengwu.utils.collection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lichengwu.utils.lang.Assert;
import cn.lichengwu.utils.lang.StringUtil;

/**
 * 集合工具类
 * 
 * @author lichengwu
 * @created 2011-11-30
 * 
 * @version 1.0
 */
final public class CollectionUtil {

	/**
	 * 将集合变成字符串
	 * 
	 * @author lichengwu
	 * @created 2011-11-30
	 * 
	 * @param collection
	 * @param separator
	 *            分隔符 默认","
	 * @return
	 */
	public static String toString(Collection<?> collection, String separator) {
		if (StringUtil.isBlank(separator)) {
			separator = ",";
		}
		StringBuilder str = new StringBuilder();
		if (collection == null) {
			str.append("null");
		} else if (collection.isEmpty()) {
			str.append("");
		} else {
			for (Object obj : collection) {
				str.append(obj.toString()).append(separator);
			}
			str.delete(str.length() - separator.length(), str.length());
		}
		return str.toString();
	}

	/**
	 * 把集合中某个字段变成字符串
	 * 
	 * @author lichengwu
	 * @created 2012-5-21
	 * 
	 * @param collection
	 * @param filedName
	 *            集合泛型对应对象字段
	 * @param separator
	 *            分隔符
	 * @return 字符串
	 */
	public static <T> String generateString(Collection<T> collection, String filedName,
	        String separator) {
		assert filedName != null;
		if (StringUtil.isBlank(separator)) {
			separator = ",";
		}
		if (collection == null) {
			return "null";
		} else if (collection.isEmpty()) {
			return "";
		}
		try {
			StringBuilder str = new StringBuilder();
			for (T obj : collection) {
				Field field = obj.getClass().getDeclaredField(filedName);
				field.setAccessible(true);
				Object object = field.get(obj);
				str.append(object.toString()).append(separator);
			}
			str.delete(str.length() - separator.length(), str.length());
			return str.toString();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将集合变成字符串
	 * 
	 * @author lichengwu
	 * @created 2011-11-30
	 * 
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String toString(Object[] array, String separator) {
		if (StringUtil.isBlank(separator)) {
			separator = ",";
		}
		StringBuilder str = new StringBuilder();
		if (array == null) {
			str.append("null");
		} else if (array.length == 0) {
			str.append("");
		} else {
			for (Object obj : array) {
				str.append(obj.toString()).append(separator);
			}
			str.delete(str.length() - separator.length(), str.length());
		}
		return str.toString();
	}

	/**
	 * 返回默认填充defaultValue的集合 如果集合支持，则清空集合原始数据
	 * 
	 * @author lichengwu
	 * @created 2011-12-1
	 * 
	 * @param <T>
	 * @param col
	 * @param size
	 *            集合大小
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static <T> Collection<T> defaultCollection(Collection<T> col, int size, T defaultValue) {
		try {
			col.clear();
		} catch (Exception e) {
		}
		for (int i = 0; i < size; i++) {
			col.add(defaultValue);
		}
		return col;
	}

	/**
	 * 获得map的子集
	 * 
	 * @author lichengwu
	 * @created 2011-12-22
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param keys
	 * @return
	 */
	public static <K, V> Map<K, V> subMap(Map<? extends K, ? extends V> map, Collection<K> keys) {
		Assert.notNull(keys, "keys 不能为空");
		Assert.notNull(map, "map 不能为空");
		Map<K, V> subMap = new HashMap<K, V>(keys.size());
		for (K key : keys) {
			V value = map.get(key);
			if (value != null) {
				subMap.put(key, value);
			}
		}
		return subMap;
	}

	/**
	 * 生成JavaBean集合的属性集合
	 * 
	 * @author lichengwu
	 * @created 2012-6-14
	 * 
	 * @param collection
	 *            JavaBean集合
	 * @param property
	 *            JavaBean属性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> generatePropertyList(Collection<?> collection, String property) {
		Assert.notNull(property);
		if (collection == null || collection.isEmpty()) {
			return null;
		}
		List<T> list = new ArrayList<T>(collection.size());
		try {
			for (Object obj : collection) {
				Field field = obj.getClass().getDeclaredField(property);
				field.setAccessible(true);
				Object object = field.get(obj);
				list.add((T) object);
			}
			return list;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

}
