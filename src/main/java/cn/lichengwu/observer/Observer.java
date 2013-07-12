/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.lichengwu.observer;

/**
 * 观察者接口
 *
 * @author lichengwu
 * @created 2012-2-18
 *
 * @version 1.0
 */
public interface Observer {
	
	/**
	 * 更新，被{@link Observable}调用以便得到最新通知。
	 * 
	 * @author lichengwu
	 * @created 2012-2-18
	 *
	 * @param observable {@link Observable}对象实例。
	 * @param arg 调用{@link Observable#notifyObservers(Object)}时传递的参数。
	 */
	void update(Observable observable,Object arg);
}
