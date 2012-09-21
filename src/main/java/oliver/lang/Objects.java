/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package oliver.lang;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Objects工具类
 * 
 * @author lichengwu
 * @created Aug 7, 2011
 * 
 * @version 1.0
 */
final public class Objects {

	private Objects() {
	}

	public static Object clone(Object object) {
		if (!(object instanceof Serializable)) {
			Enhancer enhancer = new Enhancer();
			enhancer.setInterfaces(new Class[] { Serializable.class });
			enhancer.setSuperclass(object.getClass());
			enhancer.setCallback(new MethodInterceptor() {
				public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
				        throws Throwable {
					return proxy.invoke(obj, args);
				}
			});
			object = enhancer.create();
		}

		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			bais = new ByteArrayInputStream(baos.toByteArray());
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			close(bais);
			close(ois);
			close(baos);
			close(oos);
		}
		return null;
	}

	/**
	 * 关闭流
	 * 
	 * @author lichengwu
	 * @created Aug 7, 2011
	 * 
	 * @param os
	 */
	private static void close(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
			} finally {
				os = null;
			}
		}
	}

	/**
	 * 关闭流
	 * 
	 * @author lichengwu
	 * @created Aug 7, 2011
	 * 
	 * @param is
	 */
	private static void close(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
			} finally {
				is = null;
			}
		}
	}

	public static void main(String[] args) {
		/**
		 * class for clone test
		 * 
		 * @author lichengwu
		 * @created 2012-6-16
		 * 
		 * @version 1.0
		 */
		class Oliver implements Serializable {

			private static final long serialVersionUID = -5878048307879683975L;
			private String name;

			/**
			 * @return the name
			 */
			public String getName() {
				return name;
			}

			/**
			 * @param name
			 */
			public void setName(String name) {
				this.name = name;
			}

			/**
			 * @return the age
			 */
			public int getAge() {
				return age;
			}

			/**
			 * @param age
			 */
			public void setAge(int age) {
				this.age = age;
			}

			private int age;

		}
		Oliver oliver = new Oliver();
		oliver.setAge(24);
		oliver.setName("oliver");
		Oliver clone = (Oliver) Objects.clone(oliver);
		System.out.println(clone.getName() + ":" + clone.getAge());
	}

}
