package cn.lichengwu.utils.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * ReflectionUtils
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-11-15 8:32 PM
 */
final public class ReflectionUtils {

    /**
     * Make the given filed accessible.
     * 
     * @param field
     */
    public static void makeAccessible(Field field) {
        if (!field.isAccessible()
                && (!Modifier.isPublic(field.getModifiers())
                        || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
                            .isFinal(field.getModifiers()))) {
            field.setAccessible(true);
        }
    }

    /**
     * Make the given method accessible
     * 
     * @param method
     */
    public static void makeAccessible(Method method) {
        if (!method.isAccessible()
                && (!Modifier.isPublic(method.getModifiers())
                        || !Modifier.isPublic(method.getDeclaringClass().getModifiers()) || Modifier
                            .isFinal(method.getModifiers()))) {
            method.setAccessible(true);
        }
    }

    /**
     * Make the given constructor accessible
     * 
     * @param constructor
     */
    public static void makeAccessible(Constructor<?> constructor) {
        if (!constructor.isAccessible()
                && (!Modifier.isPublic(constructor.getModifiers())
                        || !Modifier.isPublic(constructor.getDeclaringClass().getModifiers()) || Modifier
                            .isFinal(constructor.getModifiers()))) {
            constructor.setAccessible(true);
        }
    }

}
