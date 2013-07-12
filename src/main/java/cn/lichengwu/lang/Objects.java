/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.lichengwu.lang;

import java.io.*;
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

    /**
     * deep clone an object by serialization/deserialization
     * 
     * @param object
     * @return
     */
    public static <T> T clone(T object) {
        Object newObject = object;
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
            newObject = enhancer.create();
        }

        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(newObject);
            bais = new ByteArrayInputStream(baos.toByteArray());
            ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            Closer.close(bais);
            Closer.close(ois);
            Closer.close(baos);
            Closer.close(oos);
        }
        return null;
    }

    /**
     * test whether two objects are equal
     * 
     * @param obj1
     * @param obj2
     * 
     * @return whether two objects are equal
     */
    public static boolean equals(Object obj1, Object obj2) {
        return (obj1 == obj2) || (obj1 != null && obj1.equals(obj2));
        // boolean returnValue = false;
        // if (obj1 == null && obj2 == null) {
        // returnValue = true;
        // } else if (obj1 == null && obj2 != null) {
        // returnValue = false;
        // } else if (obj1 != null && obj2 == null) {
        // returnValue = false;
        // } else {
        // returnValue = obj1.equals(obj2);
        // }
        //
        // return returnValue;
    }

}
