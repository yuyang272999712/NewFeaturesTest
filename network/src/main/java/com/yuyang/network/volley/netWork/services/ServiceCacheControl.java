package com.yuyang.network.volley.netWork.services;

public class ServiceCacheControl {
    private String cacheKey;
    private long cacheDuration;
    private long refreshDuration;

    public ServiceCacheControl(String cacheKey, long cacheDuration, long refreshDuration) {
        this.cacheKey = cacheKey;
        this.cacheDuration = cacheDuration;
        this.refreshDuration = refreshDuration;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public long getCacheDuration() {
        return cacheDuration;
    }

    public long getRefreshDuration() {
        return refreshDuration;
    }
}
