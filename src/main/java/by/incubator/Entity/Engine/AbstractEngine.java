package by.incubator.Entity.Engine;

public abstract class AbstractEngine implements Startable {
    private String nameEngine;
    private double taxRoadCoefficient;

    public AbstractEngine(String nameEngine, double taxRoadCoefficient) {
        this.nameEngine = nameEngine;
        this.taxRoadCoefficient = taxRoadCoefficient;
    }

    @Override
    public String toString() {
        return nameEngine + ',' + taxRoadCoefficient;
    }

    public String getName() {
        return nameEngine;
    }

    public void setName(String nameEngine) {
        this.nameEngine = nameEngine;
    }

    public double getTaxRoadCoefficient() {
        return taxRoadCoefficient;
    }

    public void setTaxRoadCoefficient(double taxRoadCoefficient) {
        this.taxRoadCoefficient = taxRoadCoefficient;
    }
}
