/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.lichengwu.utils.pattern.observer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.junit.Test;


/**
 * 观察者模式测试
 *
 * @author lichengwu
 * @created 2012-2-18
 *
 * @version 1.0
 */
public class ObserverTest {

	@Test
	public void test() throws InterruptedException{
		final MemoryInfo info = new MemoryInfo();
		info.addObserver(new FreeMemoryDisplay());
		info.addObserver(new UsedMemoryDisplay());
		Timer timer = new Timer(false);
		timer.schedule(new TimerTask() {
			int i=0;
			@Override
			public void run() {
				i++;
				info.refresh();
				System.out.println("观察者数量:"+info.countObservers());
				if(i==10){
					info.clearObservers();;
				}
			}
		}, 500, 1000);
		TimeUnit.SECONDS.sleep(2);
	}
}
