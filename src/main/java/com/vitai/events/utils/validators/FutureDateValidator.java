package com.vitai.events.utils.validators;

import com.vitai.events.utils.annotations.FutureDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;

public class FutureDateValidator implements ConstraintValidator<FutureDate, Instant> {

    @Override
    public boolean isValid(Instant value, ConstraintValidatorContext context) {
        return value != null && value.isAfter(Instant.now());
    }

}
