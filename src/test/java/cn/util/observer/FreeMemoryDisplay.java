/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.util.observer;

import cn.lichengwu.observer.Observable;
import cn.lichengwu.observer.Observer;

/**
 *
 * @author lichengwu
 * @created 2012-2-18
 *
 * @version 1.0
 */
public class FreeMemoryDisplay implements Observer{

	/**
     * @see cn.lichengwu.observer.Observer#update(cn.lichengwu.observer.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable observable, Object arg) {
    	MemoryInfo info = (MemoryInfo) arg;
    	System.out.println("当前剩余内存："+info.getFreeMemory()/1024);
    }

}
