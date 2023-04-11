package br.com.cronos.redesocial.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;



@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidRequestValidator.class })
@Documented
public @interface ValidDto {
    String message() default "{br.com.cronos.redesocial.utils.ValidRequest.message}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
