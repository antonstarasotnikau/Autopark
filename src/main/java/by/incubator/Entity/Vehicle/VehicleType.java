package by.incubator.Entity.Vehicle;

public class VehicleType {
    private int id;
    private String typeName;
    private double taxCoefficient;

    public VehicleType() {
    }

    public VehicleType(int id, String typeName, double taxCoefficient) {
        this.id = id;
        this.typeName = typeName;
        this.taxCoefficient = taxCoefficient;
    }

    public void display(){
        System.out.println("typeName = " + typeName +
                "\ntaxCoefficient = " + taxCoefficient);
    }

    public String getString(){
        return typeName + ",\"" + taxCoefficient + "\"";
    }

    @Override
    public String toString() {
        return typeName + ",\"" + taxCoefficient + "\"";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getTaxCoefficient() {
        return taxCoefficient;
    }

    public void setTaxCoefficient(double taxCoefficient) {
        this.taxCoefficient = taxCoefficient;
    }
}
