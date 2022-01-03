package by.incubator.infrastructure.configurators.impl;

import by.incubator.infrastructure.configurators.ObjectConfigurator;
import by.incubator.infrastructure.core.Context;
import by.incubator.infrastructure.core.annotations.Autowired;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.List;

public class AutowiredObjectConfigurator implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configure(Object t, Context context) {
        Class clazz = t.getClass();
        List<Field> allFields = List.of(clazz.getDeclaredFields());
        for(Field f : allFields){
            if(f.isAnnotationPresent(Autowired.class)){
                f.setAccessible(true);
                //Method method = clazz.getMethod("set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1));

                f.set(t, context.getObject(f.getType()));
            }
        }
    }
}
