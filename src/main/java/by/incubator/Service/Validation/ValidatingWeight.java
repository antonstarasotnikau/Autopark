package by.incubator.Service.Validation;

public class ValidatingWeight implements Validating<Integer> {
    @Override
    public boolean validate(Integer weight) {
        return weight >= 0;
    }
}
