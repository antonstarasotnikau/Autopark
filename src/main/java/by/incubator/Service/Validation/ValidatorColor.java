package by.incubator.Service.Validation;

import by.incubator.Entity.Vehicle.Color;

public class ValidatorColor implements Validating<String> {
    @Override
    public boolean validate(String color) {
        String onlyColor = color.substring(color.indexOf('.') + 1);
        for(Color c : Color.values())
            if (c.name().equals(onlyColor))
                return true;
        return false;
    }
}
