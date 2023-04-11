package br.com.cronos.redesocial.utils;

import javax.validation.ConstraintValidatorContext;

public interface Dto {

    default boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
