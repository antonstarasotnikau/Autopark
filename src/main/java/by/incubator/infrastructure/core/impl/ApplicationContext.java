package by.incubator.infrastructure.core.impl;

import by.incubator.Order.Fixer;
import by.incubator.infrastructure.config.Config;
import by.incubator.infrastructure.config.impl.JavaConfig;
import by.incubator.infrastructure.core.Cache;
import by.incubator.infrastructure.core.Context;
import by.incubator.infrastructure.core.ObjectFactory;

import java.util.Map;

public class ApplicationContext implements Context {
    private final Config config;
    private final Cache cache;
    private final ObjectFactory factory;

    public ApplicationContext(String packageToScan, Map<Class<?>,Class<?>> interfaceToImplementation) {
        this.config = new JavaConfig(new ScannerImpl(packageToScan),interfaceToImplementation);
        this.cache = new CacheImpl();
        cache.put(Context.class, this);
        this.factory = new ObjectFactoryImpl(this);
    }

    @Override
    public <T> T getObject(Class<T> type) {
        T object;
        if (cache.contains(type))
            return cache.get(type);
        else if (type.isInterface()) {
            Class<? extends T> clazz = config.getImplementation(type);
            object = factory.createObject(clazz);
            cache.put((Class<T>)clazz, object);
        } else {
            object = factory.createObject(type);
            cache.put(type, object);
        }

        return object;
    }

    @Override
    public Config getConfig() {
        return this.config;
    }
}
