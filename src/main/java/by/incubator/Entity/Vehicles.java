package by.incubator.Entity;

import by.incubator.infrastructure.config.annotations.Column;
import by.incubator.infrastructure.config.annotations.ID;
import by.incubator.infrastructure.config.annotations.Table;
import lombok.*;

import java.math.BigDecimal;

@Table(name="vehicles")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehicles {
    @ID
    private Long id;
    @Column(name = "vehicle_type_id")
    private Long vehicleType;
    @Column(name = "model_name")
    private String modelName;
    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "manufacture_year")
    private Integer manufactureYear;
    @Column(name = "mileage")
    private Integer mileage;
    @Column(name = "color")
    private String color;
    @Column(name = "engine_name")
    private String engine;
    @Column(name="engine_capacity", nullable = false)
    private BigDecimal engineCapacity;
    @Column(name="fuel_consumption_per_100")
    private BigDecimal fuelConsumptionPer100;
    @Column(name="tank_capacity")
    private BigDecimal tankCapacity;

}
