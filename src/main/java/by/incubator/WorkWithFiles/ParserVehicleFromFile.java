package by.incubator.WorkWithFiles;

import by.incubator.Entity.Engine.DieselEngine;
import by.incubator.Entity.Engine.ElectricalEngine;
import by.incubator.Entity.Engine.GasolineEngine;
import by.incubator.Entity.Engine.Startable;
import by.incubator.Entity.Rent;
import by.incubator.Entity.TechnicalSpecialist;
import by.incubator.Entity.Vehicle.Color;
import by.incubator.Entity.Vehicle.Vehicle;
import by.incubator.Entity.Vehicle.VehicleType;
import by.incubator.Entity.VehicleCollection;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.core.annotations.InitMethod;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Double.parseDouble;

public class ParserVehicleFromFile implements Parser {
    @Autowired
    private TechnicalSpecialist technicalSpecialist;

    public ParserVehicleFromFile(){}

    @InitMethod
    public void init(){
    }

    public List<VehicleType> loadType(String inFile) {
        List<VehicleType> vehicleTypes = new ArrayList<>();
        Path path = Paths.get(inFile);

        try (BufferedReader br = Files.newBufferedReader(path)){
            String line = br.readLine();
            while(line != null) {
                vehicleTypes.add(createType(line));
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vehicleTypes;
    }

    public List<Vehicle> loadVehicle(String inFile, VehicleCollection vehicleCollection){
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

    public List<Rent> loadRent(String inFile, VehicleCollection vehicleCollection){
        List<Rent> rents = new ArrayList<>();
        Path path = Paths.get(inFile);

        try (BufferedReader br = Files.newBufferedReader(path)){
            String line = br.readLine();
            while(line != null) {
                String[] attributes = line.split(",");
                Vehicle v = vehicleCollection.getVehicles().get(Integer.parseInt(attributes[0]) - 1);
                List<Rent> r = v.getRents();
                String s = attributes[1].substring(6) + '.' + attributes[1].substring(3,5) + '.' + attributes[1].substring(0,2);
                Date d = new Date();
                d.setYear(Integer.parseInt(attributes[1].substring(6)));
                d.setMonth(Integer.parseInt(attributes[1].substring(3,5)));
                d.setDate(Integer.parseInt(attributes[1].substring(0,2)));
                Rent o = new Rent((long) v.getId(),d, Double.parseDouble(attributes[2]));
                r.add(o);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rents;
    }

    private VehicleType createType(String csvString) {
        String[] attributes = csvString.split(",");

        return new VehicleType(Integer.parseInt(attributes[0]), attributes[1], Double.parseDouble(attributes[2]));
    }

    private Vehicle createVehicle(String csvString, VehicleCollection vehicleCollection){
        String[] attributes = csvString.split(",");
        VehicleType vt = vehicleCollection.getVehicleTypes().get(Integer.parseInt(attributes[1]) - 1);
        Color color = Color.valueOfLabel(attributes[7]);
        Startable startable;
        if(attributes[8].equals("Electrical"))
            startable = new ElectricalEngine(parseDouble(attributes[10]), parseDouble(attributes[11]));
        else if (attributes[8].equals("Diesel"))
            startable = new DieselEngine(parseDouble(attributes[9]), Double.parseDouble(attributes[10]),Double.parseDouble(attributes[11]));
        else
            startable = new GasolineEngine(Double.parseDouble(attributes[9]), Double.parseDouble(attributes[10]), Double.parseDouble(attributes[11]));

        return new Vehicle(Integer.parseInt(attributes[0]),
                vt, attributes[2], attributes[3], Integer.parseInt(attributes[4]),
                Integer.parseInt(attributes[5]), Integer.parseInt(attributes[6]), color, startable);
    }

}
