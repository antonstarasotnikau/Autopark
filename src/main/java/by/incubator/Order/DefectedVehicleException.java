package by.incubator.Order;

public class DefectedVehicleException extends Exception{
    public DefectedVehicleException(String idVehicle){
        System.out.println("Action with vehicle " + idVehicle + " haven't been implemented");
    }
}
