package by.incubator.Order;

import by.incubator.Entity.Vehicle.Vehicle;
import by.incubator.Service.Configuration;
import by.incubator.infrastructure.core.annotations.InitMethod;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class MechanicService implements Fixer{
    static String[] details = {"Filter", "Sleeve", "Shaft", "Axis", "Candle", "Butter", "GRM", "CV joint"};
    static final String PATH_TO_FILE_ORDERS = Configuration.ORDER_FILE;
    Path path = Paths.get( PATH_TO_FILE_ORDERS);

    public MechanicService() {
    }

    @InitMethod
    public void init(){}

    @Override
    public Map<String, Integer> detectBreaking(Vehicle vehicle) {
        Map<String, Integer> order = null;

        //create random breakings
        int randomCountDetail = (int) (Math.random() * 8);
        if(randomCountDetail != 0) {
            order = new HashMap<>();
            String breakings = "";
            int countFind = 0;
            while (randomCountDetail != countFind) {
                int numberRandomDetail = (int) (Math.random() * 8);
                int countDetail = 0;
                if (randomCountDetail - countFind == 1)
                    countDetail = 1;
                else
                    countDetail = (int) (Math.random() * (randomCountDetail - countFind));
                countFind += countDetail;
                if (countDetail != 0) {
                    breakings = breakings + details[numberRandomDetail] + "," + countDetail;
                    order.put(details[numberRandomDetail], countDetail);
                    if (randomCountDetail != countFind)
                        breakings += ",";
                }
            }

            //write to file new line
            try (FileWriter writer = new FileWriter(path.toFile(), true)) {
                writer.write(vehicle.getId() + ":" + breakings + '\n');
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return order;
    }

    @Override
    public void repair(Vehicle vehicle) {
        Map<Integer,String> order = new HashMap<>();
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line = br.readLine();
            while (line != null){
                String[] attributes = line.split(":");
                order.put(Integer.parseInt(attributes[0]), attributes[1]);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.flush();
            for(Map.Entry<Integer,String> item : order.entrySet())
                if(vehicle.getId() != item.getKey())
                    writer.write(item.getKey() + ":" + item.getValue() + '\n');
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean isBroken(Vehicle vehicle) {

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line = br.readLine();
            while (line != null){
                String[] attributes = line.split(":");
                if(vehicle.getId() == Integer.parseInt(attributes[0]))
                    return true;
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int findMostDangerousVehicle(){
        int idCarWithMaxBreaking = -1;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line = br.readLine();
            int maxCountBreaking = 0;
            while (line != null){
                String[] attributes = line.split(":");
                int countBreaking = calculateCountBreaking(attributes[1]);
                if(countBreaking > maxCountBreaking) {
                    idCarWithMaxBreaking = Integer.parseInt(attributes[0]);
                    maxCountBreaking = countBreaking;
                }

                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return idCarWithMaxBreaking;
    }

    public int getCountBreaking (Vehicle vehicle) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line = br.readLine();
            while (line != null){
                String[] attributes = line.split(":");
                if(vehicle.getId() == Integer.parseInt(attributes[0])){
                    return calculateCountBreaking(attributes[1]);
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int calculateCountBreaking(String breakings){
        String[] breaking = breakings.split(",");
        int countBreaking = 0;
        for(int i = 1; i < breaking.length; i = i + 2)
            countBreaking += Integer.parseInt(breaking[i]);
        return countBreaking;
    }
}
