package by.incubator.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VehicleDto {
    public int typeId;
    private String typename;
    private double taxCoefficient;
    private String modelName;
    private int manufactureYear;
    private String registrationNumber;
    private double weight;
    private int mileage;
    private String color;
    private double tankVolume;
    private String engineName;
    private double engineTaxCoefficient;
    private int id;
    private double per100km;
    private double maxKm;
    private double tax;
    private double income;
}
