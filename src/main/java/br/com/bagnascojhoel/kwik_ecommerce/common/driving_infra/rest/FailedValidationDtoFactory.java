package br.com.bagnascojhoel.kwik_ecommerce.common.driving_infra.rest;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class FailedValidationDtoFactory {

    public List<FailedValidationDto> createAll(
            @NonNull final InvalidFormatException invalidFormatException
    ) {
        var fieldPath = invalidFormatException.getPath();
        return Collections.singletonList(FailedValidationDto.builder()
                .field(fieldPath.get(fieldPath.size() - 1).getFieldName())
                .reason("Value '{}' is not valid".replace("{}", invalidFormatException.getValue().toString()))
                .build());
    }

    public List<FailedValidationDto> createAll(
            @NonNull final MethodArgumentNotValidException methodArgumentNotValidException
    ) {
        if (methodArgumentNotValidException.getFieldErrors().isEmpty()) {
            return Collections.emptyList();
        }

        return methodArgumentNotValidException.getFieldErrors().stream()
                .map(this::create)
                .collect(Collectors.toList());
    }

    public List<FailedValidationDto> createAll(
            @NonNull final ConstraintViolationException constraintViolationException
    ) {
        if (constraintViolationException.getConstraintViolations() == null) {
            return Collections.emptyList();
        }

        return constraintViolationException.getConstraintViolations().stream()
                .map(this::create)
                .collect(Collectors.toList());
    }

    private FailedValidationDto create(final ConstraintViolation<?> constraintViolation) {
        return FailedValidationDto.builder()
                .field(constraintViolation.getMessageTemplate())
                .reason(constraintViolation.getMessageTemplate())
                .build();
    }

    private FailedValidationDto create(final FieldError fieldError) {
        return FailedValidationDto.builder()
                .field(fieldError.getDefaultMessage())
                .reason(fieldError.getDefaultMessage())
                .build();
    }
}
