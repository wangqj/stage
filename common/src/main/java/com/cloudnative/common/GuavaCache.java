package com.cloudnative.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaCache {

    public static void main(String[] args) {
        LoadingCache<String, Object> caches = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .concurrencyLevel(5)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String key) throws Exception {
                        return loadValueByKey(key);
                    }
                });
        try {
            System.out.println(caches.get("key"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    private static Map<String, Object> loadValueByKey(String key) {
    return null;
    }
}
