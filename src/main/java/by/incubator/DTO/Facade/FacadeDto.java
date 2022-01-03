package by.incubator.DTO.Facade;

import by.incubator.DTO.RentDto;
import by.incubator.DTO.VehicleDto;
import by.incubator.DTO.VehicleRepairDto;
import by.incubator.DTO.VehicleTypeDto;
import by.incubator.Entity.VehicleCollection;
import by.incubator.infrastructure.core.annotations.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class FacadeDto {
    @Autowired
    private VehicleCollection vehicleCollection;

    public FacadeDto(){}

    public List<VehicleTypeDto> getVehicleTypes(){
        return  vehicleCollection.getVehicleTypes().stream().map(vehicleType ->
                VehicleTypeDto.builder().
                id(vehicleType.getId()).
                name(vehicleType.getTypeName()).
                taxCoefficient(vehicleType.getTaxCoefficient()).build()).collect(Collectors.toList());
    }

    public List<RentDto> getRent(int id){
        return vehicleCollection.getRent(id).stream().map(rent ->
                RentDto.builder().
                vehicleId(Math.toIntExact(rent.getId())).
                dateRent(rent.getDateRent()).
                incomeRent(rent.getIncomeRent()).build()).collect(Collectors.toList());
    }

    public List<VehicleDto> getVehicles(){
        return vehicleCollection.getVehicles().stream().map(vehicle ->
                VehicleDto.builder().
                id(vehicle.getId()).
                typeId(vehicle.getVehicleType().getId()).
                typename(vehicle.getVehicleType().getTypeName()).
                taxCoefficient(vehicle.getVehicleType().getTaxCoefficient()).
                color(vehicle.getColor().name()).
                engineName(vehicle.getEngine().getName()).
                engineTaxCoefficient(vehicle.getEngine().getTaxRoadCoefficient()).
                tax(vehicle.getCalcTaxPerMonth()).
                manufactureYear(vehicle.getManufactureYear()).
                mileage(vehicle.getMileage()).
                modelName(vehicle.getModelName()).
                registrationNumber(vehicle.getRegistrationNumber()).
                tankVolume(vehicle.getEngine().getTankVolume()).
                weight(vehicle.getWeight()).
                per100km(vehicle.getEngine().getFuelPer100km()).
                maxKm(vehicle.getEngine().getMaxKilometers()).
                income(vehicle.getTotalIncome()).
                build()).collect(Collectors.toList());
    }

}
