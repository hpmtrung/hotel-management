package com.hotel.mgmt.annotations.constraints.percent;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PercentValidator implements ConstraintValidator<PercentConstaint, Integer> {

    @Override
    public void initialize(PercentConstaint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return context != null && value >= 0 && value <= 100;
    }
}
