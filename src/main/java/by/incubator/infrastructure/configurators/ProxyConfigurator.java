package by.incubator.infrastructure.configurators;

import by.incubator.infrastructure.core.Context;

public interface ProxyConfigurator {
    <T> T makeProxy(T object, Class<T> implementation, Context context) throws Exception;
}
