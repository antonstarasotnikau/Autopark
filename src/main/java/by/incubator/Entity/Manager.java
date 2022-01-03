package by.incubator.Entity;

import by.incubator.Entity.Vehicle.Vehicle;
import by.incubator.Order.Fixer;
import by.incubator.WorkWithFiles.Parser;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.threads.annotations.Schedule;

import java.util.*;

public class Manager {

    /*@Autowired
    Fixer fixer;
    @Autowired
    VehicleCollection vehicleCollection;*/

    public Manager() {
    }



    @Schedule(delta = 10000)
    public void loadVehicleAndCheck(Fixer fixer, VehicleCollection vehicleCollection) {
        vehicleCollection.init();
        List<Integer> numberNotBrokenVehicle = new ArrayList<>();
        for (Vehicle v :vehicleCollection.getVehicles())
            if (fixer.isBroken(v))
                System.out.println(v.getId() + " is broken");
            else
                numberNotBrokenVehicle.add(v.getId());

        for (Integer i : numberNotBrokenVehicle)
            System.out.println(i + " is not broken");
    }

    @Schedule(delta = 300000)
    public long loadVehicleAndCheckServlet(Fixer fixer, VehicleCollection vehicleCollection) {
        vehicleCollection.init();
        Map<Integer, Boolean> isBroken = new HashMap<>();
        for (Vehicle v :vehicleCollection.getVehicles())
            isBroken.put(v.getId(), fixer.isBroken(v));

        return (long)(new Date().getTime());

    }
}
