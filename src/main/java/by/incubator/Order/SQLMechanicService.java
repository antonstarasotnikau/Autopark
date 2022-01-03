package by.incubator.Order;

import by.incubator.Entity.Orders;
import by.incubator.Entity.Vehicle.Vehicle;
import by.incubator.Service.Configuration;
import by.incubator.Service.OrdersService;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.core.annotations.InitMethod;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLMechanicService implements Fixer{
    @Autowired
    OrdersService ordersService;

    public SQLMechanicService() {
    }

    @InitMethod
    public void init(){}

    @Override
    public Map<String, Integer> detectBreaking(Vehicle vehicle) {
        Map<String, Integer> order = null;

        return order;
    }

    @Override
    public void repair(Vehicle vehicle) {
        Map<Integer,String> order = new HashMap<>();
    }

    @Override
    public boolean isBroken(Vehicle vehicle) {
        List<Orders> orders = ordersService.getAll();

        for(Orders o : orders){
            Long idVehicle = o.getIdVehicle();
            Long id = Long.valueOf(vehicle.getId());
            if(idVehicle.equals(id))
                return true;
        }
        return false;
    }
}
