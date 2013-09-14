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
final public class ReflectionUtil {

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

    /**
     * 设置静态成员变量
     *
     * @param staticField
     * @param value
     *
     * @throws Exception
     */
    public static void setFinalStatic(Field staticField, Object value) throws Exception {
        staticField.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(staticField, staticField.getModifiers() & ~Modifier.FINAL);

        staticField.set(null, value);
    }

}
