package by.incubator.Service.Validation;

import java.util.Locale;

public class ValidatingRegistrationNumber implements Validating<String> {
    @Override
    public boolean validate(String number) {
        if (number != null) {
            if (number.length() != 9)
                return false;
            String fourNumber = number.substring(0, number.indexOf(' '));
            String twoLetter = number.substring(number.indexOf(' ') + 1, number.indexOf('-'));
            Integer region = Integer.valueOf(number.substring(number.indexOf('-') + 1));
            String regex = "\\d+";

            return fourNumber.matches(regex) && region >= 0 && region < 8 && twoLetter.equals(twoLetter.toUpperCase(Locale.ROOT));
        }

        return false;
    }
}
