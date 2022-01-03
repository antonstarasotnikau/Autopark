package by.incubator.infrastructure.config.impl;

import by.incubator.infrastructure.config.Config;
import by.incubator.infrastructure.core.Scanner;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class JavaConfig implements Config {
    private final Scanner scanner;
    private final Map<Class<?>, Class<?>> interfaceToImplementation;

    @Override
    public <T> Class<? extends T> getImplementation(Class<T> target) {
        Set<Class<? extends T>> set = new HashSet<>();
        set = scanner.getSubTypesOf(target);
        if(set.size() != 1)
            if(interfaceToImplementation.containsKey(target))
                return (Class<? extends T>) interfaceToImplementation.get(target);
            else
                throw new RuntimeException(target.getName() + set.size() +" interface has 0 or more than one impl");
        else
            return (Class<? extends T>) set.toArray()[0];
        /*return (Class<? extends T>) interfaceToImplementation.computeIfAbsent(target, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(target);
            if (classes.size() != 1) {
                throw new RuntimeException(target + " has 0 or more than one impl please update your config");
            }

            return classes.iterator().next();
        });*/
    }

    @Override
    public Scanner getScanner() {
        return this.scanner;
    }
}
