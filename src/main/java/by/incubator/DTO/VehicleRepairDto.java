package by.incubator.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VehicleRepairDto {
        private String typename;
        private String modelName;
        private int manufactureYear;
        private String registrationNumber;
        private double weight;
        private int mileage;
        private String color;
        private double tankVolume;
        private String engineName;
        private double engineTaxCoefficient;
        boolean isBroken;
        boolean isRepaired;
    }
