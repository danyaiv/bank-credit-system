package edu.rutmiit.demo.bankapicontract.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class YearValidator implements ConstraintValidator<ValidYear, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return true;
        int currentYear = java.time.Year.now().getValue();
        return value >= 1000 && value <= currentYear;
    }
}