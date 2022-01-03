package by.incubator.Test;

import by.incubator.DTO.Facade.FacadeDto;
import by.incubator.DTO.VehicleDto;
import by.incubator.Entity.Manager;
import by.incubator.Entity.VehicleCollection;
import by.incubator.Entity.Workroom;
import by.incubator.Order.Fixer;
import by.incubator.Order.SQLMechanicService;
import by.incubator.WorkWithFiles.Parser;
import by.incubator.WorkWithFiles.ParserBD.ParserVehicleFromBD;
import by.incubator.infrastructure.core.impl.ApplicationContext;
import by.incubator.infrastructure.threads.annotations.Schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainLevel14 {

    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();

        interfaceToImplementation.put(Parser.class, ParserVehicleFromBD.class);
        interfaceToImplementation.put(Fixer.class, SQLMechanicService.class);
        ApplicationContext context = new ApplicationContext("by/incubator", interfaceToImplementation);
        VehicleCollection vehicleCollection = context.getObject(VehicleCollection.class);
        FacadeDto vehicleTypeService = MainApplication.getContext().getObject(FacadeDto.class);
        Workroom workroom = context.getObject(Workroom.class);
        workroom.checkAllVehicle(vehicleCollection.getVehicles());

        vehicleTypeService = MainApplication.getContext().getObject(FacadeDto.class);
        vehicleCollection = MainApplication.getContext().getObject(VehicleCollection.class);
        workroom = MainApplication.getContext().getObject(Workroom.class);

        Manager manager;
        long startLastTime;
        manager = MainApplication.getContext().getObject(Manager.class);
        startLastTime = manager.loadVehicleAndCheckServlet(workroom.getMechanic(), vehicleCollection);
        long delta = 300000;
        /*try {
            delta = manager.getClass().getDeclaredMethod(
                    "loadVehicleAndCheck", Fixer.class, VehicleCollection.class).getAnnotation(Schedule.class).delta();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }*/
        long lastStart = new Date().getTime() - startLastTime;
        System.out.println(delta);
        System.out.println(lastStart);
        System.out.println(new Date().getTime());
        System.out.println(startLastTime);

    }
}

