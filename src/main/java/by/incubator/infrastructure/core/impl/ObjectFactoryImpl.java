package by.incubator.infrastructure.core.impl;

import by.incubator.infrastructure.configurators.ObjectConfigurator;
import by.incubator.infrastructure.configurators.ProxyConfigurator;
import by.incubator.infrastructure.core.Context;
import by.incubator.infrastructure.core.ObjectFactory;
import by.incubator.infrastructure.core.Scanner;
import by.incubator.infrastructure.core.annotations.InitMethod;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactoryImpl implements ObjectFactory {
    private final Context context;
    private final List<ObjectConfigurator> objectConfigurators;
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactoryImpl(Context context) {
        this.context = context;
        Scanner scanner = context.getConfig().getScanner();
        Set<Class<? extends ObjectConfigurator>> set = scanner.getSubTypesOf(ObjectConfigurator.class);
        objectConfigurators = new ArrayList<>();
        for(Class clazz : set){
            objectConfigurators.add((ObjectConfigurator) clazz.getDeclaredConstructor().newInstance());
        }

        Set<Class<? extends ProxyConfigurator>> set2 = scanner.getSubTypesOf(ProxyConfigurator.class);
        for(Class clazz : set2){
            proxyConfigurators.add((ProxyConfigurator) clazz.getDeclaredConstructor().newInstance());
        }
    }

    private <T> T makeProxy(Class<T> implClass, T object) throws Exception {
        for (ProxyConfigurator proxyConfigurator: proxyConfigurators)
            object = (T) proxyConfigurator.makeProxy(object, implClass, context);
        return object;
    }

    private <T> T create(Class<T> implementation) throws Exception {
        return implementation.getDeclaredConstructor().newInstance();
    }

    private <T> void configure(T object){
        for (ObjectConfigurator o : objectConfigurators)
            o.configure(object, context);
    }

    private <T> void initialize(Class<T> implementation, T object) throws Exception{
        for(Method method : implementation.getMethods()){
            if (method.isAnnotationPresent(InitMethod.class)){
                method.invoke(object);
            }
        }
    }

    @SneakyThrows
    @Override
    public <T> T createObject(Class<T> implementation) {
        T object = create(implementation);
        configure(object);
        initialize(implementation, object);
        object = makeProxy(implementation, object);
        return object;
    }
}
