package by.incubator.Service;


import by.incubator.Entity.Vehicle.Vehicle;

import java.util.Comparator;

public class CompareVehicle implements Comparator<Vehicle> {

    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        return o1.getModelName().compareTo(o2.getModelName());
    }
}
