package by.incubator.Entity;

import by.incubator.Entity.Vehicle.Vehicle;
import by.incubator.Order.BadMechanicService;
import by.incubator.Order.Fixer;
import by.incubator.Order.MechanicService;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.core.annotations.InitMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Workroom {
    @Autowired
    private Fixer mechanic;

    public Workroom() {
    }

    @InitMethod
    public void init(){
        //this.mechanic = new MechanicService();
    }

    public void checkAllVehicle(List<Vehicle> vehicles){
        List<Integer> numberNotBrokenVehicle = new ArrayList<>();
        Map<Integer, Boolean> isBroken = new HashMap<>();
        for (Vehicle v :vehicles)
            if (mechanic.isBroken(v))
                System.out.println(v.getId() + " is broken");
            else
                numberNotBrokenVehicle.add(v.getId());

        for (Integer i : numberNotBrokenVehicle)
            System.out.println(i + " is not broken");
    }

    public Map<Integer, Boolean> getInfoAllStateVehicle(List<Vehicle> vehicles){
        Map<Integer, Boolean> isBroken = new HashMap<>();
        for (Vehicle v :vehicles)
            isBroken.put(v.getId(), mechanic.isBroken(v));

        return isBroken;
    }

    public Map<Integer, Boolean> getInfoAllRepairVehicle(List<Vehicle> vehicles){
        Map<Integer, Boolean> isRepaired = new HashMap<>();
        for (Vehicle v :vehicles)
            isRepaired.put(v.getId(), mechanic.detectAndRepair(v));

        return isRepaired;
    }

    public Fixer getMechanic() {
        return mechanic;
    }

    public void setMechanic(Fixer mechanic) {
        this.mechanic = mechanic;
    }
}
