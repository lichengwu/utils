package cn.lichengwu.util.colloection;

import java.util.concurrent.atomic.AtomicReference;

/**
 * <b>SafeStack。</b>
 * <p><b>详细说明：线程安全的stack</b></p>
 * <!-- 在此添加详细说明 -->
 * 采用无锁方式实现，CAS原语实现。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Oliver</td><td>2010-11-24 下午04:11:49</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Oliver
 * @since 1.0
 */
public class SafeStack<T>
{
	
	AtomicReference<Node> top = new AtomicReference<Node>();
	
	/**
	 * <b>push。</b>  
	 * <p><b>详细说明：把项压入堆栈顶部。</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value
	 */
	public void push(T value){
		boolean success=false;
		while(!success){
			Node oldValue=top.get();
			Node newValue=new Node(oldValue,value);
			//比较并交换
			success=top.compareAndSet(oldValue,newValue);
		}
	}
	
	/**
	 * <b>peek。</b>  
	 * <p><b>详细说明：查看堆栈顶部的对象，但不从堆栈中移除它。</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return
	 */
	public T peek(){
		if(empty()){
			return null;
		}
		return top.get().value;
	}
	
	/**
	 * <b>top。</b>  
	 * <p><b>详细说明：移除堆栈顶部的对象，并作为此函数的值返回该对象。</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return
	 */
	public T top(){
		if(empty()){
			return null;
		}
		boolean success=false;
		Node oldValue=null;
		Node newValue=null;
		while(!success){
			oldValue=top.get();
			newValue=top.get().next;
			success=top.compareAndSet(oldValue,newValue);
		}
		return oldValue.value;
	}
	
	/**
	 * <b>search。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 还未实现线程安全。
	 * @param value
	 * @return
	 */
	@Deprecated
	public synchronized int search(T value){
		if(empty()){
			return -1;
		}
		boolean success=false;
		Node oldValue=top.get();
		int count=0;
		while(!success){
			if(oldValue==value){
				return count;
			}
			count++;
			oldValue=oldValue.next;
			if(oldValue==null)
				break;
		}
		count=0;
		return count;
	}
	
	/**
	 * <b>empty。</b>  
	 * <p><b>详细说明：测试堆栈是否为空。</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return
	 */
	public boolean empty(){
		return top.get()==null;
	}
	
	class Node
	{
        /**
		 * 下一个节点。
		 */
		Node next;
		/**
		 * 节点的值。
		 */
		T value;
		/**
		 * <b>构造方法。</b>  
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param next
		 * @param value
		 */
		public Node(Node next, T value) {
			this.next = next;
			this.value = value;
		}
	}
}
