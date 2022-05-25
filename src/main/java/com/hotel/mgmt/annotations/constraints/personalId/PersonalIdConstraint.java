package com.hotel.mgmt.annotations.constraints.personalId;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PersonalIdValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonalIdConstraint {
    String message() default "Invalid personalId value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
