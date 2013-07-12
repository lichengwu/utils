/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.lichengwu.utils.cache;

/**
 * 缓存对象
 * 
 * @author lichengwu
 * @created 2011-11-11
 * 
 * @version 1.0
 */
public class Cache {

    /**
     * key
     */
    private String key;
    /**
     * 缓存中的对象
     */
    private Object value;
    /**
     * 超时时间
     */
    private Long timeOut;

    /**
     * @param key
     * @param value
     * @param timeOut
     */
    public Cache(String key, Object value, Long timeOut) {
        super();
        this.key = key;
        this.value = value;
        this.timeOut = timeOut;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the timeOut
     */
    public Long getTimeOut() {
        return timeOut;
    }

    /**
     * @param timeOut
     */
    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((timeOut == null) ? 0 : timeOut.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cache other = (Cache) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (timeOut == null) {
            if (other.timeOut != null)
                return false;
        } else if (!timeOut.equals(other.timeOut))
            return false;
        return true;
    }
}
