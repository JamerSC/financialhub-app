package com.jamersc.springboot.financialhub.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.java.Log;

import java.util.Set;

public class RoleIdsNotEmptyValidator implements ConstraintValidator<RoleIdsNotEmpty, Set<Long>> {

    @Override
    public void initialize(RoleIdsNotEmpty constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Set<Long> roleIds, ConstraintValidatorContext context) {
        return roleIds != null && !roleIds.isEmpty();
    }
}
