package com.hotel.mgmt.annotations.constraints.percent;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PercentValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PercentConstaint {
    String message() default "Invalid vat value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
