/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package oliver.pattern.observer;

/**
 * 可观察对象接口
 *
 * @author lichengwu
 * @created 2012-2-18
 *
 * @version 1.0
 */
public interface Observable {

	
	/**
	 * 通知注册在此可观察对象里的所有观察者<br />
	 * 此方法与notifyObservers(null)效果相同
	 * 
	 * @author lichengwu
	 * @created 2012-2-18
	 *
	 */
	void notifyObservers();
	
	/**
	 * 通知注册在此可观察对象里的所有观察者
	 * 
	 * @author lichengwu
	 * @created 2012-2-18
	 *
	 * @param arg 传递给观察者的参数
	 */
	void notifyObservers(Object arg);
	
	/**
	 * 注册观察值
	 * 
	 * @author lichengwu
	 * @created 2012-2-18
	 *
	 * @param observer
	 */
	void  addObserver(Observer observer);
	
	/**
	 * 删除观察者
	 * 
	 * @author lichengwu
	 * @created 2012-2-18
	 *
	 * @param observer
	 */
	void removeObserver(Observer observer);
	
	/**
	 * 或者当前观察者的数量
	 * 
	 * @author lichengwu
	 * @created 2012-2-18
	 *
	 * @return
	 */
	int countObservers();
	
	/**
	 * 清空所有观察者
	 * 
	 * @author lichengwu
	 * @created 2012-2-18
	 *
	 */
	void clearObservers();
}
