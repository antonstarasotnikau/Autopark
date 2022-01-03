package by.incubator.WorkWithFiles;

import by.incubator.Entity.Vehicle.*;
import by.incubator.Entity.VehicleCollection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoadTypes implements LoadingFile<VehicleType> {
    @Override
    public List<VehicleType> load(String inFile, VehicleCollection vehicleCollection) {
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

    private VehicleType createType(String csvString) {
        String[] attributes = csvString.split(",");

        return new VehicleType(Integer.parseInt(attributes[0]), attributes[1], Double.parseDouble(attributes[2]));
    }
}
