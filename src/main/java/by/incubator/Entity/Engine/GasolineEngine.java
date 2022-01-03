package by.incubator.Entity.Engine;

public class GasolineEngine extends CombustingEngine{
    public GasolineEngine( double engineCapacity, double fuelConsumptionPer100, double fuelTankCapacity) {
        super("Gasoline", 1.1, engineCapacity, fuelConsumptionPer100, fuelTankCapacity);
    }
}
