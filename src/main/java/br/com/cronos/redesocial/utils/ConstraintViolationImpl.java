package br.com.cronos.redesocial.utils;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class ConstraintViolationImpl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Name field with error", example = "name")
    private final String field;

    @Schema(description = "Description of error or problem with field", example = "Name is large", required = true)
    private final String message;

    private ConstraintViolationImpl(ConstraintViolation<?> violation) {
        this.message = violation.getMessage();
        this.field = Stream.of(violation.getPropertyPath().toString().split("\\.")).skip(2).collect(Collectors.joining("."));
    }

    public ConstraintViolationImpl(String field, String message) {
        this.message = message;
        this.field = field;
    }

    public static ConstraintViolationImpl of(ConstraintViolation<?> violation) {
        return new ConstraintViolationImpl(violation);
    }

    public static ConstraintViolationImpl of(String violation) {
        return new ConstraintViolationImpl(null, violation);
    }
}
