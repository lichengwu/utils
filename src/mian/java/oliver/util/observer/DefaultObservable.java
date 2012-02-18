/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package oliver.util.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable的默认实现
 * 
 * @author lichengwu
 * @created 2012-2-18
 * 
 * @version 1.0
 */
public abstract class DefaultObservable implements Observable {

	private boolean changed = false;

	private List<Observer> observers = new ArrayList<Observer>();

	/**
	 * @see oliver.util.observer.Observable#notifyObservers()
	 */
	@Override
	public void notifyObservers() {
		notifyObservers(null);
	}

	/**
	 * @see oliver.util.observer.Observable#notifyObservers(java.lang.Object)
	 */
	@Override
	public void notifyObservers(Object arg) {
		if (!changed) {
			return;
		}
		clearChanged();
		for (Observer observer : observers) {
			observer.update(this, arg);
		}

	}

	/**
	 * @see oliver.util.observer.Observable#addObserver(oliver.util.observer.Observer)
	 */
	@Override
	public void addObserver(Observer observer) {
		if (observer == null) {
			throw new NullPointerException();
		}
		synchronized (observers) {
			if (!observers.contains(observer)) {
				observers.add(observer);
			}
		}

	}

	/**
	 * @see oliver.util.observer.Observable#removeObserver(oliver.util.observer.Observer)
	 */
	@Override
	public void removeObserver(Observer observer) {
		synchronized (observers) {
			observers.remove(observer);
		}
	}

	/**
	 * @see oliver.util.observer.Observable#countObservers()
	 */
	@Override
	public int countObservers() {
		return observers.size();
	}

	/**
	 * 数据是否发生变化
	 * 
	 * @author lichengwu
	 * @created 2012-2-18
	 * 
	 * @return 如果数据发生变化，返回true；否则返回false。
	 */
	protected boolean changed() {
		return changed;
	}

	/**
	 * 设置数据已发生变化
	 * 
	 * @author lichengwu
	 * @created 2012-2-18
	 * 
	 */
	protected synchronized void setChanged() {
		this.changed = true;
	}

	/**
	 * 清除数据变化状态
	 * 
	 * @author lichengwu
	 * @created 2012-2-18
	 * 
	 */
	protected synchronized void clearChanged() {
		this.changed = false;
	}

	/**
	 * @see oliver.util.observer.Observable#clearObservers()
	 */
	@Override
	public void clearObservers() {
		synchronized (observers) {
			observers.clear();
		}
	}

}
