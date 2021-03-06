/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.lichengwu.utils.date;

import java.util.Calendar;
import java.util.Date;

import cn.lichengwu.utils.lang.Assert;

/**
 * 日历工具类
 * 
 * @author lichengwu
 * @created 2011-11-30
 * 
 * @version 1.0
 */
final public class CalendarUtil {

	private CalendarUtil() {
	}

	/**
	 * 一天的秒数
	 */
	public static final long ONE_DAY_IN_SECONDS = 24 * 60 * 60L;

	/**
	 * 一天的毫秒数
	 */
	public static final long ONE_DAY_IN_MILLISECONDS = ONE_DAY_IN_SECONDS * 1000;

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
	 * 获得上个星期几对应的日期 eg:今天是2011-12-20 如果要获得从今天开始的上个周五对应的日期
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
	 *            com.sankuai.meituan.ct.common.util.CalendarUtil.Week
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

	/**
	 * <pre>
	 * 获得下个星期几对应的日期 eg:今天是2012-02-21 如果要获得从今天开始的下个周五对应的日期
	 * getNextDayInWeek(new Date(), Week.FRIDAY)
	 * 返回2012-02-24 对应的日期
	 * 
	 * <pre>
	 * 
	 * @author lichengwu
	 * @created 2012-2-21
	 * 
	 * @param date
	 * @param week com.sankuai.meituan.ct.common.util.CalendarUtil.Week
	 * @return
	 */
	public static Date getNextDayInWeek(Date date, Week week) {
		Assert.notNull(date, "日期不能为空");
		Assert.notNull(week, "星期不能为空");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		int day = week.getIndex() - getWeek(date).getIndex();
		if (day < 0) {
			day = 7 + day;
		}
		if (day == 0) {
			day = 7;
		}
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/**
	 * 获得这个星期中的某天
	 * 
	 * @author lichengwu
	 * @created 2012-2-15
	 * 
	 * @param date
	 * @param week
	 * @return
	 */
	public static Date getDateInThisWeek(Date date, Week week) {
		Assert.notNull(date, "日期不能为空");
		Assert.notNull(week, "星期不能为空");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		int index = getWeek(date).getIndex();
		int day = (week.getIndex() == 0 ? 7 : week.getIndex()) - (index == 0 ? 7 : index);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	public static void main(String[] args) {
		// System.out.println(getDaysOfMonth(new Date()));
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(new Date());
		// System.out.println(cal.get(Calendar.DAY_OF_WEEK)-1);
		// System.out.println(getWeek(2011, 12, 30).getName());
		// System.out.println(DateUtil.Date2String(getLastDayInWeek(
		// DateUtil.stirng2Date("2011-12-03", DateUtil.DefaultShortFormat),
		// Week.MONDAY)));
		// System.out.println(DateUtil.Date2String(getLastDayInWeek(
		// DateUtil.stirng2Date("2011-12-03", DateUtil.DefaultShortFormat),
		// Week.TUESDAY)));
		// System.out.println(DateUtil.Date2String(getLastDayInWeek(
		// DateUtil.stirng2Date("2011-12-03", DateUtil.DefaultShortFormat),
		// Week.WEDNESDAY)));
		// System.out.println(DateUtil.Date2String(getLastDayInWeek(
		// DateUtil.stirng2Date("2011-12-03", DateUtil.DefaultShortFormat),
		// Week.THURSDAY)));
		// System.out.println(DateUtil.Date2String(getLastDayInWeek(
		// DateUtil.stirng2Date("2011-12-03", DateUtil.DefaultShortFormat),
		// Week.FRIDAY)));
		// System.out.println(DateUtil.Date2String(getLastDayInWeek(
		// DateUtil.stirng2Date("2011-12-03", DateUtil.DefaultShortFormat),
		// Week.SATURDAY)));
		// System.out.println(DateUtil.Date2String(getLastDayInWeek(
		// DateUtil.stirng2Date("2011-12-03", DateUtil.DefaultShortFormat),
		// Week.SUNDAY)));
		// System.out.println(DateUtil.Date2String(getNextDayInWeek(
		// DateUtil.stirng2Date("2012-02-28", DateUtil.DefaultShortFormat),
		// Week.MONDAY)));
		// System.out.println(DateUtil.Date2String(getNextDayInWeek(
		// DateUtil.stirng2Date("2012-02-28", DateUtil.DefaultShortFormat),
		// Week.TUESDAY)));
		// System.out.println(DateUtil.Date2String(getNextDayInWeek(
		// DateUtil.stirng2Date("2012-02-28", DateUtil.DefaultShortFormat),
		// Week.WEDNESDAY)));
		// System.out.println(DateUtil.Date2String(getNextDayInWeek(
		// DateUtil.stirng2Date("2012-02-28", DateUtil.DefaultShortFormat),
		// Week.THURSDAY)));
		// System.out.println(DateUtil.Date2String(getNextDayInWeek(
		// DateUtil.stirng2Date("2012-02-28", DateUtil.DefaultShortFormat),
		// Week.FRIDAY)));
		// System.out.println(DateUtil.Date2String(getNextDayInWeek(
		// DateUtil.stirng2Date("2012-02-28", DateUtil.DefaultShortFormat),
		// Week.SATURDAY)));
		// System.out.println(DateUtil.Date2String(getNextDayInWeek(
		// DateUtil.stirng2Date("2012-02-28", DateUtil.DefaultShortFormat),
		// Week.SUNDAY)));
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

		/**
		 * 星期一
		 */
		MONDAY("一", 1),

		/**
		 * 星期二
		 */
		TUESDAY("二", 2),

		/**
		 * 星期三
		 */
		WEDNESDAY("三", 3),

		/**
		 * 星期四
		 */
		THURSDAY("四", 4),

		/**
		 * 星期五
		 */
		FRIDAY("五", 5),

		/**
		 * 星期六
		 */
		SATURDAY("六", 6),

		/**
		 * 星期日
		 */
		SUNDAY("日", 0);

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