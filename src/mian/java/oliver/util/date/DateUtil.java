package oliver.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import oliver.util.math.Random;

/**
 * <b>DateUtils。</b>
 * <p><b>详细说明：时间工具类</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Oliver</td><td>2010-10-23 下午08:33:55</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Oliver
 * @since 1.0
 */
public final class DateUtil
{
	
	public static final String DefaultLongFormat="yyyy-MM-d hh:mm:ss";
	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	private DateUtil() {
	}
	/**
	 * <b>随机时间。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 通过指定格式的字符串获得在一定时间范围内的随见日期。
	 * @param beginDate beginDate 起始日期，格式为：yyyy-MM-dd
	 * @param endDate endDate 结束日期，格式为：yyyy-MM-dd
	 * @return 在起始日期和结束日期之间的一个日期
	 * @throws ParseException
	 */
	public static Date randomDate(String beginDate, String endDate)
			throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = sdf.parse(beginDate);
		Date end = sdf.parse(endDate);
		long date = Random.rangeRandom(begin.getTime(), end.getTime());
		return new Date(date);
	}

	/**
	 * <b>随机时间。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获得指定时间范围内的一个随机日期。
	 * @param beginDate 起始日期
	 * @param endDate 结束日期
	 * @return 在起始日期和结束日期之间的一个日期
	 * @throws ParseException
	 */
	public static Date randomDate(Date beginDate, Date endDate)
			throws ParseException
	{
		long date = Random.rangeRandom(beginDate.getTime(), endDate.getTime());
		return new Date(date);
	}
	/**
	 * <b>从Date中获得时间。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 返回一个格式化好的时间。
	 * @param date 一个Date对象
	 * @param format 格式为 HH：mm:ss等
	 * @return 时间字符串
	 */
	public static String getTime(Date date,String format)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dateStr=null;
		try
		{
			dateStr=sdf.format(date);
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		return dateStr;
	}
	
	/**
	 * <b>从Date中获得时间。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 返回一个格式化好的时间。
	 * @param date 一个Date对象
	 * @param format 格式为 HH：mm:ss等
	 * @return 时间字符串
	 */
	public static String getTime(java.sql.Date date,String format)
	{
		Date d= new java.sql.Date(date.getTime());
		return getTime(d,format);
	}
	
	/**
	 * 时间转换成字符串
	 * 
	 * @author lichengwu
	 * @created 2011-12-30
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2String(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * parse string to {@link Date}
	 * 
	 * @author lichengwu
	 * @created 2012-6-9
	 *
	 * @param dateString 
	 * @param format
	 * @return {@link Date}
	 */
	public static Date string2Date(String dateString,String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        dateString = dateString.trim();
            try {
	            return formatter.parse(dateString);
            } catch (ParseException e) {
	            throw new RuntimeException(e);
            }
    }
	
	   /**
     * 将时间转换成昨天
     * 
     * @author lichengwu
     * @created 2011-11-1
     * 
     * @param date
     * @return
     */
    public static Date toYesterday(Date date) {
        return add(date, Calendar.DAY_OF_YEAR, -1);
    }

    /**
     * 将时间转换成明天
     * 
     * @author lichengwu
     * @created 2011-11-1
     * 
     * @param date
     * @return
     */
    public static Date toTommorow(Date date) {
        return add(date, Calendar.DAY_OF_YEAR, 1);
    }
	
	private static Date add(Date date, int field, int value) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.add(field, value);
        return cal.getTime();
    }
}
