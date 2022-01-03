package by.incubator.Entity.Engine;

public abstract class CombustingEngine extends AbstractEngine{

    private double engineCapacity;
    private double tankVolume;
    private double fuelPer100km;

    public CombustingEngine(String nameEngine, double taxRoadCoefficient, double engineCapacity, double fuelTankCapacity, double fuelConsumptionPer100) {
        super(nameEngine, taxRoadCoefficient);
        this.engineCapacity = engineCapacity;
        this.tankVolume = fuelTankCapacity;
        this.fuelPer100km = fuelConsumptionPer100;
    }

    @Override
    public double getTaxPerMonth() {
        return 0;
    }

    @Override
    public double getMaxKilometers() {
        return (tankVolume * 100 / fuelPer100km);
    }

    public double getTankVolume() {
        return tankVolume;
    }

    public void setTankVolume(double tankVolume) {
        this.tankVolume = tankVolume;
    }

    public double getFuelPer100km() {
        return fuelPer100km;
    }

    public void setFuelPer100km(double fuelPer100km) {
        this.fuelPer100km = fuelPer100km;
    }
}
