/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package oliver.util.date;

import java.util.Calendar;
import java.util.Date;

import oliver.util.Assert;

/**
 * 日历工具类
 * 
 * @author lichengwu
 * @created 2011-11-30
 * 
 * @version 1.0
 */
final public class CalendarUtil {

	/**
	 * 返回给定时间所在月份的天数
	 * 
	 * @author lichengwu
	 * @created 2011-11-30
	 * 
	 * @param date
	 * @return
	 */
	public static int getDaysOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回给定时间所在月份的天数
	 * 
	 * @author lichengwu
	 * @created 2011-11-30
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfMonth(Integer year, Integer month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回时间的星期
	 * 
	 * @author lichengwu
	 * @created 2011-11-30
	 * 
	 * @param date
	 * @return
	 */
	public static Week getWeek(Integer year, Integer month, Integer day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		return Week.getWEEK(cal.get(Calendar.DAY_OF_WEEK) - 1);
	}

	/**
	 * 返回时间的星期
	 * 
	 * @author lichengwu
	 * @created 2011-11-30
	 * 
	 * @param date
	 * @return
	 */
	public static Week getWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		return Week.getWEEK(cal.get(Calendar.DAY_OF_WEEK) - 1);
	}

	/**
	 * <pre>
	 * 获得上个星期几对于的日期 eg:今天是2011-12-20 如果要获得从今天开始的上个周五对应的日期
	 * getLastDayInWeek(new Date(), Week.FRIDAY)
	 * 返回2011-12-16 对应的日期
	 * 
	 * <pre>
	 * 
	 * @author lichengwu
	 * @created 2011-12-20
	 * 
	 * @param date
	 * @param week
	 *     
	 * @return
	 */
	public static Date getLastDayInWeek(Date date, Week week) {
		Assert.notNull(date, "日期不能为空");
		Assert.notNull(week, "星期不能为空");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		int day = getWeek(date).getIndex() - week.getIndex();
		if (day < 0) {
			day = 7 + day;
		}
		if (day == 0) {
			day = 7;
		}
		cal.add(Calendar.DAY_OF_MONTH, 0 - day);
		return cal.getTime();
	}

	public static void main(String[] args) {
		// System.out.println(getDaysOfMonth(new Date()));
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(new Date());
		// System.out.println(cal.get(Calendar.DAY_OF_WEEK)-1);
		System.out.println(getWeek(2011, 12, 30).getName());
	}

	/**
	 * 星期
	 * 
	 * @author lichengwu
	 * @created 2011-11-30
	 * 
	 * @version 1.0
	 */
	public static enum Week {
		MONDAY("一", 1), TUESDAY("二", 2), WEDNESDAY("三", 3), THURSDAY("四", 4), FRIDAY(
				"五", 5), SATURDAY("六", 6), SUNDAY("日", 0);

		private String name;

		private int index;

		Week(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public static String getName(int index) {
			for (Week week : Week.values()) {
				if (week.getIndex() == index) {
					return week.name;
				}
			}
			return null;
		}

		public static Integer getIndex(String name) {
			for (Week week : Week.values()) {
				if (week.getName().equals(name)) {
					return week.index;
				}
			}
			return null;
		}

		public static Week getWEEK(int index) {
			for (Week week : Week.values()) {
				if (week.getIndex() == index) {
					return week;
				}
			}
			return null;
		}

		public int getIndex() {
			return index;
		}

		public String getName() {
			return name;
		}
	}
}