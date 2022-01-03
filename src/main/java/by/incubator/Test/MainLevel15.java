package by.incubator.Test;

import by.incubator.Entity.Manager;
import by.incubator.Entity.VehicleCollection;
import by.incubator.Entity.Workroom;
import by.incubator.Order.Fixer;
import by.incubator.Order.SQLMechanicService;
import by.incubator.WorkWithFiles.Parser;
import by.incubator.WorkWithFiles.ParserBD.ParserVehicleFromBD;
import by.incubator.infrastructure.core.impl.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class MainLevel15 {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();

        interfaceToImplementation.put(Parser.class, ParserVehicleFromBD.class);
        interfaceToImplementation.put(Fixer.class, SQLMechanicService.class);
        ApplicationContext context = new ApplicationContext("by/incubator", interfaceToImplementation);

        Manager manager = context.getObject(Manager.class);
        Fixer fixer = context.getObject(Fixer.class);
        VehicleCollection vehicleCollection = context.getObject(VehicleCollection.class);

        manager.loadVehicleAndCheck(fixer, vehicleCollection);
        try {
            Thread.currentThread().sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
