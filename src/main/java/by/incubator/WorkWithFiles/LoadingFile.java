package by.incubator.WorkWithFiles;

import by.incubator.Entity.VehicleCollection;

import java.util.List;

public interface LoadingFile<T> {
    List<T> load(String inFile, VehicleCollection vehicleCollection);
}
