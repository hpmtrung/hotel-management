package com.hotel.mgmt.annotations.constraints.personalId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PersonalIdValidator implements ConstraintValidator<PersonalIdConstraint, String> {

    @Override
    public void initialize(PersonalIdConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return context != null && value != null && !value.isBlank() && value.matches("^[\\d]{12}$");
    }
}
