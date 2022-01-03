package by.incubator.Entity.Engine;

public class DieselEngine extends CombustingEngine{
    public DieselEngine(double engineCapacity, double fuelConsumptionPer100, double fuelTankCapacity) {
        super("Diesel", 1.2, engineCapacity, fuelConsumptionPer100, fuelTankCapacity);
    }
}
