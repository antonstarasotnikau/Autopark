package by.incubator.Service.Validation;

public class ValidatorManufacturedYear implements Validating<Integer> {
    public static final int LOWER_LIMIT_MANUFACTURE_YEAR = 1886;

    @Override
    public boolean validate(Integer year) {
        return year >= LOWER_LIMIT_MANUFACTURE_YEAR;
    }
}
