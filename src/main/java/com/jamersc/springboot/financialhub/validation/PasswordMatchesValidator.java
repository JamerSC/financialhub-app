package com.jamersc.springboot.financialhub.validation;

import com.jamersc.springboot.financialhub.dto.UserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator <PasswordMatches, UserDto> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        // Check if the password match
        if (userDto.getPassword() == null || userDto.getConfirmPassword() == null) {
            return false;
        }
        boolean isValid = userDto.getPassword().equals(userDto.getConfirmPassword());
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("confirmPassword").addConstraintViolation();
        }
        return isValid;
    }
}
