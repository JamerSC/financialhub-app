package com.jamersc.springboot.financialhub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RoleIdsNotEmptyValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleIdsNotEmpty {
    String message() default "At least one role must be selected";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
