package by.incubator.Entity;

import by.incubator.Entity.Vehicle.*;
import by.incubator.Service.*;
import by.incubator.WorkWithFiles.*;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.core.annotations.InitMethod;

import java.util.*;

public class VehicleCollection {

    //static public List<VehicleType> vehicleTypes;
    private List<VehicleType> vehicleTypes = new ArrayList<>();
    //static public List<Vehicle> vehicles;
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Rent> allRent = new ArrayList<>();

    @Autowired
    private Parser parser;

    public VehicleCollection() {
    }

    @InitMethod
    public void init(){
        vehicleTypes = parser.loadType(Configuration.VEHICLE_TYPE_FILE);
        vehicles = parser.loadVehicle(Configuration.VEHICLE_FILE, this);
        parser.loadRent(Configuration.RENT_FILE, this);
    }


    public List<Rent> getAllRent() {
        for(Vehicle v : vehicles)
            allRent.addAll(v.getRents());
        return allRent;
    }

    public List<Rent> getRent(int id) {
        return vehicles.get(id).getRents();
    }

    public void setAllRent(List<Rent> allRent) {
        this.allRent = allRent;
    }

    public List<VehicleType> getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(List<VehicleType> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void insert(int index, Vehicle vehicle) {
        if (index < 0 || index >= vehicles.size())
            vehicles.add(vehicle);

        vehicles.add(index, vehicle);
    }

    public int delete(int index) {
        if (index < 0 || index >= vehicles.size())
            return -1;

        vehicles.remove(index);

        return index;
    }

    public double sumTotalProfit(){
        double result = 0.0;

        for(Vehicle v: vehicles){
            for(Rent r: v.getRents())
                result += r.getIncomeRent();
        }

        return result;
    }

    public void display(){
        System.out.println("ID" + "\t" + "Type" + "\t" + " ModelName " + "\t" + "Number " + "\t" + "Weight (kg) " + "\t" + "Year " + "\t" + "Mileage " + "\t" + "Color " + "\t" + "Income " + "\t" + "Tax " + "\t" + "Profit");
        Double result = 0.0;
        for (Vehicle v : vehicles) {
            System.out.println(v.getId() + "\t" + v.getVehicleType().getTypeName() + "\t" + v.getModelName() + "\t" + v.getRegistrationNumber()+ "\t" + v.getMileage()
                    + "\t" + v.getManufactureYear() + "\t" + v.getMileage() + "\t" + v.getColor().name() + "\t" + v.getTotalIncome() + "\t" + v.getCalcTaxPerMonth() + "\t" + (v.getTotalIncome() - v.getCalcTaxPerMonth()));
            result += (v.getTotalIncome() - v.getCalcTaxPerMonth());
        }

        System.out.println("Total:" + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + result);
    }

    public void sort(Comparator<Vehicle> vehicleComparator) {
        Collections.sort(vehicles,vehicleComparator);
    }

}
