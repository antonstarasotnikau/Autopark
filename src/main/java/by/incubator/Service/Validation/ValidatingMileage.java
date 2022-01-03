package by.incubator.Service.Validation;

public class ValidatingMileage implements Validating<Integer> {
    @Override
    public boolean validate(Integer mileage) {
        return mileage >= 0;
    }
}
