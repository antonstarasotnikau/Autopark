package by.incubator.WorkWithFiles;


import by.incubator.Entity.Rent;
import by.incubator.Entity.Vehicle.Vehicle;
import by.incubator.Entity.VehicleCollection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoaderRents implements LoadingFile<Rent> {

    @Override
    public List<Rent> load(String inFile, VehicleCollection vehicleCollection) {
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
                Rent o = new Rent((long) v.getId(), d, Double.parseDouble(attributes[2]));
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
}
