package br.com.bagnascojhoel.kwik_ecommerce.common.infrastructure.driving_ports.rest.resource_representations;

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
public final class FailedValidationRepresentationFactory {

    public List<FailedValidationRepresentation> createAll(
            @NonNull final InvalidFormatException invalidFormatException
    ) {
        var fieldPath = invalidFormatException.getPath();
        return Collections.singletonList(FailedValidationRepresentation.builder()
                .field(fieldPath.get(fieldPath.size() - 1).getFieldName())
                .reason("Value '{}' is not valid".replace("{}", invalidFormatException.getValue().toString()))
                .build());
    }

    public List<FailedValidationRepresentation> createAll(
            @NonNull final MethodArgumentNotValidException methodArgumentNotValidException
    ) {
        if (methodArgumentNotValidException.getFieldErrors().isEmpty()) {
            return Collections.emptyList();
        }

        return methodArgumentNotValidException.getFieldErrors().stream()
                .map(this::create)
                .collect(Collectors.toList());
    }

    public List<FailedValidationRepresentation> createAll(
            @NonNull final ConstraintViolationException constraintViolationException
    ) {
        if (constraintViolationException.getConstraintViolations() == null) {
            return Collections.emptyList();
        }

        return constraintViolationException.getConstraintViolations().stream()
                .map(this::create)
                .collect(Collectors.toList());
    }

    private FailedValidationRepresentation create(final ConstraintViolation<?> constraintViolation) {
        return FailedValidationRepresentation.builder()
                .field(constraintViolation.getMessageTemplate())
                .reason(constraintViolation.getMessageTemplate())
                .build();
    }

    private FailedValidationRepresentation create(final FieldError fieldError) {
        return FailedValidationRepresentation.builder()
                .field(fieldError.getDefaultMessage())
                .reason(fieldError.getDefaultMessage())
                .build();
    }
}
