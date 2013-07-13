/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.lichengwu.utils.pattern.observer;

import cn.lichengwu.utils.pattern.observer.DefaultObservable;

/**
 * 内存信息
 * 
 * @author lichengwu
 * @created 2012-2-18
 * 
 * @version 1.0
 */
public class MemoryInfo extends DefaultObservable {
	private long freeMemory;

	private long usedMemory;

	public void refresh() {
		freeMemory = Runtime.getRuntime().freeMemory();
		usedMemory = Runtime.getRuntime().totalMemory() - freeMemory;
		setChanged();
		notifyObservers(this);
	}

	/**
	 * @return the freeMemory
	 */
	public long getFreeMemory() {
		return freeMemory;
	}

	/**
	 * @return the usedMemory
	 */
	public long getUsedMemory() {
		return usedMemory;
	}

	// /**
	// * @see oliver.util.observer.DefaultObservable#notifyObservers()
	// */
	// @Override
	// public void notifyObservers() {
	// super.notifyObservers(this);
	// }
}
