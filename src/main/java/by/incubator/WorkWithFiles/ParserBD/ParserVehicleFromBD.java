package by.incubator.WorkWithFiles.ParserBD;

import by.incubator.Entity.*;
import by.incubator.Entity.Engine.DieselEngine;
import by.incubator.Entity.Engine.ElectricalEngine;
import by.incubator.Entity.Engine.GasolineEngine;
import by.incubator.Entity.Engine.Startable;
import by.incubator.Entity.Vehicle.Color;
import by.incubator.Entity.Vehicle.Vehicle;
import by.incubator.Entity.Vehicle.VehicleType;
import by.incubator.Service.RentsService;
import by.incubator.Service.TypesService;
import by.incubator.Service.VehiclesService;
import by.incubator.WorkWithFiles.Parser;
import by.incubator.infrastructure.core.annotations.Autowired;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;

public class ParserVehicleFromBD implements Parser {
    @Autowired
    private TypesService typesService;
    @Autowired
    private VehiclesService vehiclesService;
    @Autowired
    private RentsService rentsService;


    public List<VehicleType> loadType(String inFile) {
        List<Types> types = typesService.getAll();
        List<VehicleType> vehicleTypes = new ArrayList<>();
        for(Types type: types)
            vehicleTypes.add(entityToObjectType(type));

        return vehicleTypes;
    }

    private VehicleType entityToObjectType(Types type) {
        return new VehicleType(Math.toIntExact(type.getId()), type.getName(), type.getCoefTax().doubleValue());
    }

    @Override
    public List<Vehicle> loadVehicle(String inFile, VehicleCollection vehicleCollection) {
        List<Vehicles> vehicles  = vehiclesService.getAll();
        List<Vehicle> vehicleList = new ArrayList<>();

        for(Vehicles v : vehicles) {
            VehicleType vehicleType = vehicleCollection.getVehicleTypes().get(Math.toIntExact(v.getVehicleType() - 1));
            vehicleList.add(entityToObjectVehicle(v, vehicleType));
        }

        return vehicleList;
    }

    private Vehicle entityToObjectVehicle(Vehicles v, VehicleType vehicleType) {
        Startable startable;
        if(v.getEngine().equals("Electrical"))
            startable = new ElectricalEngine(v.getFuelConsumptionPer100().doubleValue(), v.getTankCapacity().doubleValue());
        else if (v.getEngine().equals("Diesel"))
            startable = new DieselEngine(v.getEngineCapacity().doubleValue(), v.getFuelConsumptionPer100().doubleValue(), v.getTankCapacity().doubleValue());
        else
            startable = new GasolineEngine(v.getEngineCapacity().doubleValue(), v.getFuelConsumptionPer100().doubleValue(), v.getTankCapacity().doubleValue());
        return new Vehicle(Math.toIntExact(v.getId()), vehicleType, v.getModelName(), v.getRegistrationNumber(),v.getWeight(),
                v.getManufactureYear(), v.getMileage(), Color.valueOf(v.getColor()), startable);
    }

    @Override
    public List<Rent> loadRent(String inFile, VehicleCollection vehicleCollection) {
        List<Rents> rents = rentsService.getAll();
        List<Rent> rentList = new ArrayList<>();

        for(Rents r : rents) {
            Rent rent = entityToObjectRent(r, vehicleCollection.getVehicles().get(Math.toIntExact(r.getIdVehicle() - 1)));
            rentList.add(rent);
            vehicleCollection.getVehicles().get(Math.toIntExact(r.getIdVehicle()) - 1).getRents().add(rent);
        }

        return rentList;
    }

    private Rent entityToObjectRent(Rents r, Vehicle vehicle) {
        return new Rent((long) vehicle.getId(),r.getData(), r.getIncomeRent().doubleValue());
    }

}
