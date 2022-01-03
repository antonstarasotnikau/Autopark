package by.incubator.Test;


import by.incubator.Order.Fixer;
import by.incubator.Order.SQLMechanicService;
import by.incubator.WorkWithFiles.Parser;
import by.incubator.WorkWithFiles.ParserBD.ParserVehicleFromBD;
import by.incubator.infrastructure.core.Context;
import by.incubator.infrastructure.core.impl.ApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

public class MainApplication{
//public class MainApplication implements ServletContextListener{

    private volatile static Context context;

    private MainApplication() {

    }

    /*@Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Parser.class, ParserVehicleFromBD.class);
        interfaceToImplementation.put(Fixer.class, SQLMechanicService.class);
        context = new ApplicationContext("by/incubator", interfaceToImplementation);
    }*/

    public static Context createContext() {

        if (context == null) {
            Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
            interfaceToImplementation.put(Parser.class, ParserVehicleFromBD.class);
            interfaceToImplementation.put(Fixer.class, SQLMechanicService.class);
            context = new ApplicationContext("by/incubator", interfaceToImplementation);
        }

        return context;
    }

    public static Context getContext() {
        if (context == null) {
            context = createContext();
        }
        return context;
    }
}
