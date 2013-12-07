package cn.lichengwu.utils.btrace;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;

import java.util.*;

/**
 * trace new object/array
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-11-14 11:17 PM
 */
@BTrace
public class NewObject {

        private static String classPrefix = "com";


        //    @OnMethod(clazz = "/.*/", method = "/.*/", location = @Location(value = Kind.NEWARRAY, clazz = "char"))
        //        public static void traceNewArray() {
        //        trace(classPrefix);
        //    }



        @OnMethod(clazz = "java.lang.String", method = "<init>")
        public static void traceNewObject() {
            String str = BTraceUtils.jstackStr();



            for (String currentClass : str.split("\\n")) {
                for (String prefix : classPrefix.split(",")) {
                    if (BTraceUtils.Strings.endsWith(currentClass, prefix)) {
                        if (!classCounterMap.containsKey(currentClass)) {
                            classCounterMap.put(currentClass, 1);
                        } else {
                            classCounterMap.put(currentClass, classCounterMap.get(currentClass) + 1);
                        }
                        iCount++;
                        break;
                    }
                }
            }
        }


        @OnTimer(200)
        public static void print() {
            BTraceUtils.println("==================    RESULT    ===================");
            for (Map.Entry<String, Integer> entry : classCounterMap.entrySet()) {
                BTraceUtils.println(entry.getKey() + "\t\t" + entry.getValue());
            }

            BTraceUtils.print(BTraceUtils.Strings.concat("==================TOTAL:", BTraceUtils.Strings.str(iCount)));
            BTraceUtils.println("===================");

        }

        private static Map<String, Integer> classCounterMap = BTraceUtils.newHashMap();

        private static int iCount = 0;
}
