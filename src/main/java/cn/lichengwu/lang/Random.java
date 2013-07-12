package cn.lichengwu.lang;

/**
 * <b>RandomUtils。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Oliver</td><td>2008-10-19 上午11:46:39</td><td>建立类型</td></tr>
 * <tr><td>2</td><td>Oliver</td><td>2010-10-23 下午8:58:23</td><td>修改完善</td></tr>
 * 
 * </table>
 * @version 1.02
 * @author Oliver
 * @since 1.0
 */
public final class Random
{
	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	private Random() {
	}
	/**
	 * <b>rangeRandom。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 返回在一定范围内的整数。
	 * 返回的整数包含最小边界，不包含最大边界。
	 * @param begin 边界1
	 * @param end  边界2
	 * @return 随机整数
	 */
	public static int rangeRandom(int begin, int end)
	{
		double result = Math.min(begin, end) + Math.random()
				* Math.abs(begin - end);
		if (result > Math.max(begin, end) || result < Math.min(begin, end))
		{
			rangeRandom(begin, end);
		}
		return (int) result;
	}

	/**
	 * <b>rangeRandom。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 返回在一定范围内的Double数。
	 * 返回的Double数包含最小边界，不包含最大边界。
	 * @param begin 边界1
	 * @param end  边界2
	 * @return 随机Double数
	 */
	public static double rangeRandom(double begin, double end)
	{
		double result = Math.min(begin, end) + Math.random()
				* Math.abs(begin - end);
		if (result > Math.max(begin, end) || result < Math.min(begin, end))
		{
			rangeRandom(begin, end);
		}
		return result;
	}

	/**
	 * <b>rangeRandom。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 返回在一定范围内的Float数。
	 * 返回的Float数包含最小边界，不包含最大边界。
	 * @param begin 边界1
	 * @param end  边界2
	 * @return 随机Float数
	 */
	public static float rangeRandom(float begin, float end)
	{
		double result = Math.min(begin, end) + Math.random()
				* Math.abs(begin - end);
		if (result > Math.max(begin, end) || result < Math.min(begin, end))
		{
			rangeRandom(begin, end);
		}
		return (float) result;
	}

	/**
	 * <b>rangeRandom。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 返回在一定范围内的long数。
	 * 返回的long数包含最小边界，不包含最大边界。
	 * @param begin 边界1
	 * @param end  边界2
	 * @return 随机long数
	 */
	public static long rangeRandom(long begin, long end)
	{
		double result = Math.min(begin, end) + Math.random()
				* Math.abs(begin - end);
		if (result > Math.max(begin, end) || result < Math.min(begin, end))
		{
			rangeRandom(begin, end);
		}
		return (long) result;
	}
}
