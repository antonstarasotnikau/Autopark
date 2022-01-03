package by.incubator.infrastructure.configurators.impl;

import by.incubator.infrastructure.configurators.ObjectConfigurator;
import by.incubator.infrastructure.core.Context;
import by.incubator.infrastructure.core.annotations.Property;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class PropertyObjectConfigurator implements ObjectConfigurator {
    private final Map<String, String> properties;

    @SneakyThrows
    public PropertyObjectConfigurator() {
        URL path = this.getClass().getClassLoader().getResource("application.properties");
        if(path == null)
            throw new FileNotFoundException(String.format("File '%s' not found", "application.properties"));
        Stream<String> lines = new BufferedReader(new InputStreamReader(path.openStream())).lines();
        properties = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    @Override
    @SneakyThrows
    public void configure(Object t, Context context) {
        Field[] fields = t.getClass().getDeclaredFields();
        for(Field f : fields){
            if(f.isAnnotationPresent(Property.class)){
                if(f.getAnnotation(Property.class).value().isEmpty()){
                    if(properties.containsKey(f.getName())){
                        f.setAccessible(true);
                        f.set(t, properties.get(f.getName()));
                    }
                } else {
                    f.setAccessible(true);
                    f.set(t, f.getAnnotation(Property.class).value());
                }
            }
        }
    }
}
