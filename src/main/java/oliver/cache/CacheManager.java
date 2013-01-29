/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package oliver.cache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存管理
 * 
 * @author lichengwu
 * @created 2011-11-11
 * 
 * @version 1.0
 */
public final class CacheManager {

    public static final long ONE_DAY_TIMEOUT = 24 * 60 * 60 * 1000L;

    public static final long HALF_AN_HOUR_TIMEOUT = 30 * 60 * 1000L;

    public static final long ONE_HOUR_TIMEOUT = 60 * 60 * 1000L;

    private static final Logger logger = LoggerFactory.getLogger(CacheManager.class);

    private Map<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();

    /**
     * 初始化
     * 
     * @author lichengwu
     * @created 2011-11-11
     * 
     */
    @PostConstruct
    protected void init() {
        // List<AclGroup> lines = getLines();
        // long current = System.currentTimeMillis();
        // putCache(new Cache(FLOW_LINES, lines, LINE_TIME_OUT + current));
        // logger.info("cache manager initd");
    }

    /**
     * 将缓存放入缓存管理器
     * 
     * @author lichengwu
     * @created 2011-11-17
     * 
     * @param cache
     */
    public void put(Cache cache) {
        synchronized (cacheMap) {
            assert cache != null;
            assert cache.getKey() != null;
            cacheMap.put(cache.getKey(), cache);
        }
    }

    /**
     * <pre>
     * 从缓存管理器中获得缓存
     * 如果缓存不存在或者缓存过期，返回null
     * 
     * <pre>
     * 
     * @author lichengwu
     * @created 2011-11-17
     * 
     * @param key
     * @return
     */
    public Cache get(String key) {
        Cache cache = null;
        cache = cacheMap.get(key);
        if (cache == null) {
            logger.warn("failed to get cache[{}]", key);
        } else if (cache.getTimeOut() < System.currentTimeMillis()) {
            logger.warn("cache[{}] expired", key);
            cacheMap.remove(key);
            cache = null;
        }

        return cache;
    }

    /**
     * <pre>
     * 合并缓存
     * 如果缓存管理器不存在这个cahe，则添加
     * 以失效时间大的缓存为准
     * 
     * <pre>
     * 
     * @author lichengwu
     * @created 2011-11-17
     * 
     * @param cache 合并后的缓存
     * @return
     */
    public Cache merge(Cache cache) {
        synchronized (cacheMap) {
            Cache storedCache = cacheMap.get(cache.getKey());
            if (storedCache == null) {
                storedCache = cache;
                cacheMap.put(cache.getKey(), storedCache);

            } else if (storedCache.getTimeOut() < cache.getTimeOut()) {
                storedCache = cache;
                cacheMap.put(storedCache.getKey(), storedCache);
            }
            return storedCache;
        }
    }

    /**
     * 返回缓存管理器中缓存的个数
     * 
     * @author lichengwu
     * @created 2011-11-17
     * 
     * @return
     */
    public int size() {
        return cacheMap.size();
    }

    /**
     * 清除所有缓存
     * 
     * @author lichengwu
     * @created 2011-11-11
     * 
     */
    public void clear() {
        synchronized (cacheMap) {
            cacheMap.clear();
            init();
            logger.info("clear cache manager");
        }
    }

    /**
     * 根据key清除缓存
     * 
     * @author lichengwu
     * @created 2011-11-17
     * 
     * @param key
     */
    public void clear(String key) {
        Cache cache = cacheMap.remove(key);
        if (cache != null) {
            logger.info("clear cache[{}] from cache manager", key);
        }
    }

    /**
     * 清除缓存集合
     * 
     * @author lichengwu
     * @created 2011-11-17
     * 
     * @param keys
     */
    public void clear(Collection<String> keys) {
        synchronized (cacheMap) {
            if (keys == null) {
                logger.warn("cache keys null");
                return;
            } else {
                for (String key : keys) {
                    clear(key);
                }
            }
        }
    }

}
