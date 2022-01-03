package by.incubator.WorkWithFiles;

import by.incubator.Entity.Rent;
import by.incubator.Entity.Vehicle.Vehicle;
import by.incubator.Entity.Vehicle.VehicleType;
import by.incubator.Entity.VehicleCollection;

import java.util.List;

public interface Parser {
    List<VehicleType> loadType(String inFile);
    List<Vehicle> loadVehicle(String inFile, VehicleCollection vehicleCollection);
    List<Rent> loadRent(String inFile, VehicleCollection vehicleCollection);
}
