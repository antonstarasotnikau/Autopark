package by.incubator.Service.Validation;

public class ValidatingVehicleType implements Validating<String> {
    @Override
    public boolean validate(String type) {//Bus,"1.2"
        String model = type.substring(0, type.indexOf(','));
        Double taxCoefficient = Double.valueOf(type.substring(type.indexOf(',') + 2, type.length() -1));
        return !model.isEmpty() && !taxCoefficient.isNaN() && (taxCoefficient >= 0);
    }
}
