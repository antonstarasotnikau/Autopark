package by.incubator.WorkWithFiles.ParserBD;

import by.incubator.Entity.*;

import java.util.List;

public interface ParserVehicleInterface {
    List<Types> loadType(String inFile);
    List<Vehicles> loadVehicle(String inFile);
    List<Rents> loadRent(String inFile);
}
