/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.lichengwu.utils.collection;

/**
 * 快速访问极值的栈
 * 
 * @author lichengwu
 * @created 2012-9-9
 * 
 * @version 1.0
 */
public class FastExtremeValueAccessStack<T extends Comparable<T>> {

	private static final int INIT_CAPACITY = 8;

	private Object[] elementData;

	private Object[] maxData;

	private Object[] minData;

	private int currentIndex = -1;

	private int maxIndex = -1;

	private int minIndex = -1;

	/**
	 * 默认构造函数
	 */
	public FastExtremeValueAccessStack() {
		elementData = new Object[INIT_CAPACITY];
		maxData = new Object[INIT_CAPACITY];
		minData = new Object[INIT_CAPACITY];
	}

	/**
	 * @param initCapacity
	 */
	public FastExtremeValueAccessStack(int initCapacity) {
		if (initCapacity <= 0) {
			throw new IllegalArgumentException("栈大小必须是个正数");
		}
		elementData = new Object[initCapacity];
		maxData = new Object[initCapacity];
		minData = new Object[initCapacity];
	}

	/**
	 * 详细说明：查看堆栈顶部的对象，但不从堆栈中移除它。
	 * 
	 * @author lichengwu
	 * @created 2012-9-9
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T peek() {
		if (empty()) {
			return null;
		}
		return (T) elementData[currentIndex];
	}

	/**
	 * 详细说明：移除堆栈顶部的对象，并作为此函数的值返回该对象。
	 * 
	 * @author lichengwu
	 * @created 2012-9-9
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T pop() {
		if (empty()) {
			return null;
		}
		T e = (T) elementData[currentIndex];
		elementData[currentIndex--] = null;
		T max = max();
		T min = min();
		if (max != null && max.compareTo(e) == 0) {
			maxData[maxIndex--] = null;
		}
		if (min != null && min.compareTo(e) == 0) {
			minData[minIndex--] = null;
		}
		return e;
	}

	/**
	 * 详细说明：把项压入堆栈顶部。
	 * 
	 * @author lichengwu
	 * @created 2012-9-9
	 * 
	 * @param e
	 */
	public void push(T e) {
		ensureCapacity();
		T max = max();
		T min = min();
		if (max == null || e.compareTo(max) > 0) {
			maxData[++maxIndex] = e;
		}
		if (min == null || e.compareTo(min) < 0) {
			minData[++minIndex] = e;
		}
		elementData[++currentIndex] = e;
	}

	/**
	 * 获得当前栈的最大值
	 * 
	 * @author lichengwu
	 * @created 2012-9-9
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T max() {
		if (empty()) {
			return null;
		}
		return (T) maxData[maxIndex];
	}

	/**
	 * 获得当前栈的最小值
	 * 
	 * @author lichengwu
	 * @created 2012-9-9
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T min() {
		if (empty()) {
			return null;
		}
		return (T) minData[minIndex];
	}

	/**
	 * 测试堆栈是否为空。
	 * 
	 * @author lichengwu
	 * @created 2012-9-9
	 * 
	 * @return
	 */
	public boolean empty() {
		return currentIndex < 0;
	}

	/**
	 * 扩充容量
	 * 
	 * @author lichengwu
	 * @created 2012-9-9
	 * 
	 */
	private void ensureCapacity() {
		if (currentIndex == elementData.length - 1) {
			Object[] newElementData = new Object[elementData.length * 3 / 2];
			System.arraycopy(elementData, 0, newElementData, 0, elementData.length);
			elementData = newElementData;

			Object[] newMaxData = new Object[maxData.length * 3 / 2];
			System.arraycopy(maxData, 0, newMaxData, 0, maxData.length);
			maxData = newMaxData;

			Object[] newMinData = new Object[minData.length * 3 / 2];
			System.arraycopy(minData, 0, newMinData, 0, minData.length);
			minData = newMinData;
		}
	}
}
