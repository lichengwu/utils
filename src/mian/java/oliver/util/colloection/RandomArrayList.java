package oliver.util.colloection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * <b>RandomArrayList。</b>
 * <p><b>详细说明：随机ArrayList</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Oliver</td><td>2010-10-23 下午09:46:03</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Oliver
 * @since 1.0
 */
public class RandomArrayList<E> extends ArrayList<E>
{

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -4295448242824965220L;
	
	/**
	 * <b>random。</b>  
	 * <p><b>详细说明：返回ArrayList中的一个随机元素。</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return
	 */
	public E random(){
		return get(new Random().nextInt(this.size()));
	}

	public RandomArrayList()
	{
		super();
	}

	public RandomArrayList(Collection<? extends E> c)
	{
		super(c);
	}

	public RandomArrayList(int initialCapacity)
	{
		super(initialCapacity);
	}

}
