package by.incubator.Service.Validation;

public class ValidatingModelName implements Validating<String> {
    @Override
    public boolean validate(String name) {
        return !name.isEmpty();
    }
}
