package by.incubator.WorkWithFiles;


import by.incubator.Entity.Engine.*;
import by.incubator.Entity.Vehicle.*;
import by.incubator.Entity.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;

public class LoadVehicle implements LoadingFile<Vehicle> {
    @Override
    public List<Vehicle> load(String inFile, VehicleCollection vehicleCollection) {
        List<Vehicle> vehicles = new ArrayList<>();
        Path path = Paths.get(inFile);

        try (BufferedReader br = Files.newBufferedReader(path)){
            String line = br.readLine();
            while(line != null) {
                vehicles.add(createVehicle(line, vehicleCollection));
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    private Vehicle createVehicle(String csvString, VehicleCollection vehicleCollection) {
        String[] attributes = csvString.split(",");
        VehicleType vt = vehicleCollection.getVehicleTypes().get(Integer.parseInt(attributes[1]) - 1);
        Color color = Color.valueOfLabel(attributes[7]);
        Startable startable;
        if(attributes[8].equals("Electrical"))
            startable = new ElectricalEngine(parseDouble(attributes[9]), parseDouble(attributes[10]));
        else if (attributes[8].equals("Diesel"))
            startable = new DieselEngine(parseDouble(attributes[9]), Double.parseDouble(attributes[10]),Double.parseDouble(attributes[11]));
        else
            startable = new GasolineEngine(Double.parseDouble(attributes[9]), Double.parseDouble(attributes[10]), Double.parseDouble(attributes[11]));

        return new Vehicle(Integer.parseInt(attributes[0]),
                vt, attributes[2], attributes[3], Integer.parseInt(attributes[4]),
                Integer.parseInt(attributes[5]), Integer.parseInt(attributes[6]), color, startable);
    }
}
