package by.incubator.Test;

import by.incubator.Entity.VehicleCollection;
import by.incubator.Entity.Workroom;
import by.incubator.Order.BadMechanicService;
import by.incubator.Order.Fixer;
import by.incubator.Order.MechanicService;
import by.incubator.Service.Validation.Validating;
import by.incubator.WorkWithFiles.Parser;
import by.incubator.WorkWithFiles.ParserVehicleFromFile;
import by.incubator.infrastructure.core.Scanner;
import by.incubator.infrastructure.core.impl.ApplicationContext;
import by.incubator.infrastructure.core.impl.ScannerImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainLevel13 {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();

        interfaceToImplementation.put(Fixer.class, MechanicService.class);
        interfaceToImplementation.put(Parser.class, ParserVehicleFromFile.class);

        ApplicationContext context = new ApplicationContext("by/incubator", interfaceToImplementation);
        VehicleCollection vehicleCollection = context.getObject(VehicleCollection.class);
        Workroom workroom = context.getObject(Workroom.class);
        workroom.checkAllVehicle(vehicleCollection.getVehicles());

    }
}
