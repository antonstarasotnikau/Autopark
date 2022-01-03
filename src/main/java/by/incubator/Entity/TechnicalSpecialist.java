package by.incubator.Entity;



import by.incubator.Entity.Vehicle.Color;
import by.incubator.infrastructure.core.annotations.InitMethod;

import java.util.Locale;

public class TechnicalSpecialist {

    public TechnicalSpecialist() {
    }

    @InitMethod
    public void init(){

    }

    public static final int LOWER_LIMIT_MANUFACTURE_YEAR = 1886;

    static public boolean validateManufacturedYear(int year){
        return year >= LOWER_LIMIT_MANUFACTURE_YEAR;
    }

    static public boolean validateMileage(int mileage) {
        return mileage >= 0;
    }

    static public boolean validateWeight(int weight) {
        return weight >= 0;
    }

    static public boolean validateColorString(String color) {
        String onlyColor = color.substring(color.indexOf('.') + 1);
        for(Color c : Color.values())
            if (c.name().equals(onlyColor))
                return true;
        return false;
    }

    static public boolean validateVehicleType(String type) {//Bus,"1.2"
        String model = type.substring(0, type.indexOf(','));
        Double taxCoefficient = Double.valueOf(type.substring(type.indexOf(',') + 2, type.length() -1));
        return !model.isEmpty() && !taxCoefficient.isNaN() && (taxCoefficient >= 0);
    }

    static public boolean validateRegistrationNumber (String number) {
        if (number != null) {
            if (number.length() != 9)
                return false;
            String fourNumber = number.substring(0, number.indexOf(' '));
            String twoLetter = number.substring(number.indexOf(' ') + 1, number.indexOf('-'));
            int region = Integer.parseInt(number.substring(number.indexOf('-') + 1));
            String regex = "\\d+";

            return fourNumber.matches(regex) && region >= 0 && region < 8 && twoLetter.equals(twoLetter.toUpperCase(Locale.ROOT));
        }

        return false;
    }

    static public boolean validateModelName(String name) {
        return !name.isEmpty();
    }
}
