package com.cold.tutorial.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by faker on 2015/10/19.
 */
public class MyCacheManager<T> {
    private Map<String, T> cache = new ConcurrentHashMap<>();

    public T getValue(String name) {
        return cache.get(name);
    }

    public void addOrUpdate(String name, T t) {
        cache.put(name, t);
    }

    public void remove(String name) {
        if (cache.containsKey(name)) {
            cache.remove(name);
        }
    }

    public void removeAll() {
        cache.clear();
    }
}
