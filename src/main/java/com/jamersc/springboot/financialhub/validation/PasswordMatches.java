package com.jamersc.springboot.financialhub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordMatchesValidator.class) // helper class, business rules or logic
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME) // process on runtime
public @interface PasswordMatches {

    // define error message
    public String message() default "Password do not match";
    // define default groups
    Class<?>[] groups() default {};
    // define default payloads - it gives additional message in error occur.
    Class<? extends Payload>[] payload() default  {};
}