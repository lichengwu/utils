package cn.lichengwu.utils.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Cache注解
 *
 * @author 佐井
 * @version 1.0
 * @created 2013-11-21 11:27 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {

    /**
     * 缓存作用域
     *
     * @return
     */
    public Scope scope() default Scope.GLOBAL;

    /**
     * 缓存的存储类型
     *
     * @return
     */
    public Storage storage() default Storage.HEAP;

    /**
     * 是否缓存方法异常
     * @return
     */
    public boolean cacheException() default false;

    /**
     * cache的作用域
     *
     * @author 佐井
     * @version 1.0
     * @created 2013-11-21 11:31 PM
     */
    public enum Scope {

        HTREAD,
        GLOBAL
    }

    public enum Storage {
        HEAP,
        DISK
    }

}


