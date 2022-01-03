package by.incubator.Entity.Vehicle;

import by.incubator.Entity.Engine.AbstractEngine;
import by.incubator.Entity.Engine.Startable;
import by.incubator.Entity.Rent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehicle implements Comparable{
    private final int id;
    private final VehicleType vehicleType;
    private final String modelName;
    private String registrationNumber;
    private int weight;
    private final int manufactureYear;
    private int mileage;
    private Color color;
    private Startable engine;
    private List<Rent> rents;

    public Vehicle(int id, VehicleType vehicleType, String modelName, String registrationNumber, int weight, int manufactureYear, int mileage, Color color, Startable engine){
           this.id = id;

           this.vehicleType =vehicleType;

            this.modelName = modelName;

            this.registrationNumber = registrationNumber;

            this.weight = weight;

            this.manufactureYear = manufactureYear;

            this.mileage = mileage;

            this.color = color;

            this.engine = engine;

            rents = new ArrayList<Rent>();

    }

    @Override
    public String toString() {
        return vehicleType.getString() + "," + modelName + "," + registrationNumber + "," +
                weight + "," + manufactureYear + "," + mileage + "," + color.toString() + "," + engine.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return vehicleType.equals(vehicle.vehicleType) && modelName.equals(vehicle.modelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleType, modelName);
    }

    @Override
    public int compareTo(Object o) {
        Vehicle secondVehicle = (Vehicle) o;

        if (this.manufactureYear > secondVehicle.getManufactureYear()) {
            return 1;
        }
        else if (this.manufactureYear < secondVehicle.getManufactureYear()) {
            return -1;
        }
        else if (this.mileage > secondVehicle.getMileage()) {
            return 1;
        } else if (this.mileage < secondVehicle.getMileage()) {
            return -1;
        } else
            return 0;
    }

    public static void sortArray(Vehicle[] array){
        Vehicle tempVehicle;

        for(int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++)
                if(array[i].compareTo(array[j]) > 0) {
                    tempVehicle = array[i];
                    array[i] = array[j];
                    array[j] = tempVehicle;
                }
        }
    }

    public double getCalcTaxPerMonth() {
        return (weight * 0.0013 + vehicleType.getTaxCoefficient() *
                ((AbstractEngine)this.engine).getTaxRoadCoefficient() * 30 + 5);
    }

    public double getTotalIncome(){
        double result = 0.0;
        for(Rent r :this.rents)
            result += r.getIncomeRent();
        return result;
    }

    public double getTotalProfit() {
        return (this.getTotalIncome() - this.getCalcTaxPerMonth());
    }

    static public class Display {
        public static void display(Vehicle[] array) {
            for (int i = 0; i < array.length; i++)
                System.out.println(array[i].toString());
        }
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getModelName() {
        return modelName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber){ this.registrationNumber = registrationNumber; }

    public int getWeight() { return weight; }

    public void setWeight(int weight){ this.weight = weight;}

    public int getManufactureYear() {
        return manufactureYear;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage){ this.mileage = mileage;}

    public Color getColor() {
        return color;
    }

    public void setColor(Color color){ this.color = color;}

    public int getId() { return id;}

    public List<Rent> getRents() { return rents;}

    public void setRents(List<Rent> rents) { this.rents = rents;}


    public Startable getEngine() {
        return engine;
    }

    public void setEngine(Startable engine) {
        this.engine = engine;
    }
}
