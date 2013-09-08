package cn.lichengwu.utils.lang;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Stack;

/**
 * measure object size in jvm
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-08-08 1:57 PM
 */
public class ObjectMeasurement {

    private static Instrumentation inst;

    /**
     * invoke by agent
     *
     * @param agentArgs
     * @param instP
     */
    public static void premain(String agentArgs, Instrumentation instP) {
        inst = instP;
    }

    /**
     * current object size
     * <br />
     * -javaagent:
     *
     * @param o
     *
     * @return
     */
    public static long sizeOf(Object o) {
        if (inst == null) {
            throw new IllegalStateException("Can not access instrumentation environment.\n" +
                    "Please check if jar file containing SizeOfAgent class is \n" +
                    "specified in the java's \"-javaagent\" command line argument.");
        }
        long objectSize = inst.getObjectSize(o);
//        System.out.println(o.getClass().getName() + "=" +objectSize);
        return objectSize;
    }

    /**
     * deep size of an object
     *
     * @param obj
     *
     * @return
     */
    public static long deepSizeOf(Object obj) {
        // map for trace calculated objects
        Map<Object, Object> visited = new IdentityHashMap<Object, Object>();
        Stack<Object> stack = new Stack<Object>();
        long result = getCurrentSizeAndTraceField(obj, stack, visited);
        //iterator stack
        while (!stack.isEmpty()) {
            result += getCurrentSizeAndTraceField(stack.pop(), stack, visited);
        }
        //clean
        visited.clear();
        stack.clear();
        return result;
    }

    /**
     * test whether the object is measured or null
     *
     * @param obj
     * @param visited
     *
     * @return
     */
    private static boolean needCalculate(Object obj, Map<Object, Object> visited) {
        if (obj instanceof String) {
            if (obj != ((String) obj).intern()) {
                return false;
            }
        }
        return (obj != null) && !visited.containsKey(obj);
    }

    /**
     * @param obj
     * @param stack
     * @param visited
     *
     * @return
     */
    private static long getCurrentSizeAndTraceField(Object obj, Stack<Object> stack, Map<Object, Object> visited) {
        if (!needCalculate(obj, visited)) {
            return 0;
        }
        // put current object ot stack
        visited.put(obj, null);
        traceField2Stack(obj, stack);
        return sizeOf(obj);
    }

    /**
     * @param obj
     * @param stack
     *
     * @return
     */
    private static void traceField2Stack(Object obj, Stack<Object> stack) {

        Class<?> clazz = obj.getClass();
        if (clazz.isArray()) {
            // skip primitive type array
            if (clazz.getName().length() != 2) {
                int length = Array.getLength(obj);
                for (int i = 0; i < length; i++) {
                    stack.add(Array.get(obj, i));
                }
            }
        }

        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                // ignore static member
                if (!Modifier.isStatic(field.getModifiers())) {
                    //ignore primitive member, sizeOf() method already calculated
                    if (field.getType().isPrimitive()) {
                        continue;
                    } else {
                        field.setAccessible(true);
                        try {
                            Object filed = field.get(obj);
                            if (filed != null) {
                                //put to stack
                                stack.add(filed);
                            }
                        } catch (IllegalAccessException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
            //recursion until no super class
            clazz = clazz.getSuperclass();
        }
    }

}
