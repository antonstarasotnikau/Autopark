package by.incubator.infrastructure.core.impl;

import by.incubator.infrastructure.core.Cache;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CacheImpl implements Cache {
    private Map<String, Object> cache;//String - name class; Object - objest

    public CacheImpl() {
        this.cache = new HashMap<>();
    }

    @Override
    public boolean contains(Class<?> clazz) {
        return cache.containsKey(clazz.getSimpleName());
    }

    @Override
    public <T> T get(Class<T> clazz) {
        return (T) cache.get(clazz.getSimpleName());
    }

    @Override
    public <T> void put(Class<T> clazz, T value) {
        cache.put(clazz.getSimpleName(),value);
    }
}
