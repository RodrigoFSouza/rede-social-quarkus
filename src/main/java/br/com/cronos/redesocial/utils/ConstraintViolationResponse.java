package br.com.cronos.redesocial.utils;

import lombok.Getter;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ConstraintViolationResponse {
    private final List<ConstraintViolationImpl> errors = new ArrayList<>();

    private ConstraintViolationResponse(ConstraintViolationException exception) {
        exception.getConstraintViolations().forEach(violation -> errors.add(ConstraintViolationImpl.of(violation)));
    }

    public static ConstraintViolationResponse of(ConstraintViolationException exception) {
        return new ConstraintViolationResponse(exception);
    }
}
