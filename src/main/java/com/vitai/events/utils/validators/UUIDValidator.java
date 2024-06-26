package com.vitai.events.utils.validators;

import com.vitai.events.utils.annotations.ValidUUID;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.UUID;

public class UUIDValidator implements ConstraintValidator<ValidUUID, String> {

    @Override
    public void initialize(ValidUUID constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        try {
            UUID.fromString(value);

            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}