package cn.lichengwu.utils.colloection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * 随机ArrayList
 * 
 * @author lichengwu
 * @created 2010-10-23
 * 
 * @version 1.0
 */
public class RandomArrayList<E> extends ArrayList<E> {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -4295448242824965220L;

	/**
	 * 详细说明：返回ArrayList中的一个随机元素。
	 * 
	 * @author lichengwu
	 * @created 2010-10-23
	 * 
	 * @return
	 */
	public E random() {
		return get(new Random().nextInt(this.size()));
	}

	public RandomArrayList() {
		super();
	}

	public RandomArrayList(Collection<? extends E> c) {
		super(c);
	}

	public RandomArrayList(int initialCapacity) {
		super(initialCapacity);
	}

}
