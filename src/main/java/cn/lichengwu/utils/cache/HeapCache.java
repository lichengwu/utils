/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.lichengwu.utils.cache;

import cn.lichengwu.utils.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * cache witch use JVM head storage
 *
 * @author lichengwu
 * @version 1.0
 * @created 2011-11-11
 */
public final class HeapCache {

    private static final Object NULL = new Object();

    public static final long ONE_DAY_TIMEOUT = 24 * 60 * 60 * 1000L;

    public static final long HALF_AN_HOUR_TIMEOUT = 30 * 60 * 1000L;

    public static final long ONE_HOUR_TIMEOUT = 60 * 60 * 1000L;

    public static final long TIME_OUT_FOREVER = -1L;

    private static final Logger log = LoggerFactory.getLogger(HeapCache.class);

    private Map<Integer, Cache> cacheMap = null;


    /**
     * init cache
     */
    protected void init() {
        if (log.isDebugEnabled()) {
            log.debug("start init cache");
        }
        cacheMap = new ConcurrentHashMap<Integer, Cache>();
        if (log.isDebugEnabled()) {
            log.debug("cache init successful");
        }
    }


    /**
     * put {@linkplain Serializable} to cache
     *
     * @param key     a string key
     * @param value   a {@linkplain Serializable} value
     * @param timeOut cache time out, default {@link HeapCache#TIME_OUT_FOREVER}
     */
    public void put(String key, Serializable value, long timeOut) {
        final long _timeOut = timeOut <= 0 ? TIME_OUT_FOREVER : timeOut + System.currentTimeMillis();

        final int innerKey = getKey(key);
        cacheMap.put(innerKey, new Cache(value, _timeOut));
    }

    /**
     * put {@linkplain Serializable} to cache which will never expire
     *
     * @param key   a string key
     * @param value a {@linkplain Serializable} value
     */
    public void put(String key, Serializable value) {
        put(key, value, TIME_OUT_FOREVER);
    }

    /**
     * get value from cache
     * <p/>
     * if the cache referenced by key expired or not exists return {@code null}
     *
     * @param key a string key
     * @return cache, if the cache referenced by key expired or not exists return {@code null}
     */
    public Serializable get(String key) {
        int innerKey = getKey(key);
        Cache cache = cacheMap.get(innerKey);
        if (cache == null) {
            if (log.isDebugEnabled()) {
                log.debug("failed to get cache for key[{}]", key);
            }
            return null;
        } else if (cache.timeOut != TIME_OUT_FOREVER && cache.timeOut < System.currentTimeMillis()) {
            if (log.isDebugEnabled()) {
                log.debug("cache for key[{}] expired", key);
            }
            cacheMap.remove(innerKey);
            return null;
        }
        return cache.value;

    }

    /**
     * get key for inner map
     *
     * @param key
     * @return
     */
    private int getKey(String key) {
        if (StringUtil.isBlank(key)) {
            return NULL.hashCode();
        }
        return key.hashCode();
    }

    /**
     * get the cache size
     *
     * @return cache size
     */
    public int size() {
        return cacheMap.size();
    }

    /**
     * clear all cache
     */
    public void clear() {
        synchronized (cacheMap) {
            cacheMap.clear();
            init();
            if (log.isDebugEnabled()) {
                log.debug("clear all caches");
            }
        }
    }

    /**
     * clear the cache hold by key
     *
     * @param key
     */
    public void clear(String key) {
        int innerKey = getKey(key);
        Cache cache = cacheMap.remove(innerKey);
        if (cache != null) {
            if (log.isDebugEnabled()) {
                log.debug("clear cache for key[{}]", key);
            }
        }
    }

    /**
     * clear caches hold by keys
     *
     * @param keys a key collection
     */
    public void clear(Collection<String> keys) {
        synchronized (cacheMap) {
            for (String key : keys) {
                clear(key);
            }
        }
    }


    /**
     * inner class for {@linkplain HeapCache}
     */
    private static class Cache implements Serializable {

        private static final long serialVersionUID = 4369883466994027714L;

        private Serializable value;

        private long timeOut;

        /**
         * @param value
         * @param timeOut
         */
        public Cache(Serializable value, long timeOut) {
            super();
            this.value = value;
            this.timeOut = timeOut;
        }

    }


}


