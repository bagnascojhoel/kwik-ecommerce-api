package br.com.bagnascojhoel.kwik_ecommerce.product.infrastructure.driving_ports.rest;

import br.com.bagnascojhoel.kwik_ecommerce.common.domain.AbstractResourceNotFoundException;
import br.com.bagnascojhoel.kwik_ecommerce.common.infrastructure.driving_ports.rest.resource_representations.ErrorRepresentation;
import br.com.bagnascojhoel.kwik_ecommerce.common.infrastructure.driving_ports.rest.resource_representations.FailedValidationRepresentation;
import br.com.bagnascojhoel.kwik_ecommerce.common.infrastructure.driving_ports.rest.resource_representations.FailedValidationRepresentationFactory;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class RestExceptionHandler {

    private final FailedValidationRepresentationFactory failedValidationRepresentationFactory;

    @ExceptionHandler(AbstractResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorRepresentation handleResourceNotFound(final HttpServletRequest httpServletRequest, final AbstractResourceNotFoundException abstractResourceNotFoundException) {
        return ErrorRepresentation.builder()
                .typeUri("/errors/client-errors/not-founds/" + abstractResourceNotFoundException.getErrorCode())
                .title(Objects.requireNonNullElse(abstractResourceNotFoundException.getMessage(), "Resource not found"))
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRepresentation handleMethodArgumentNotValid(
            final HttpServletRequest httpServletRequest,
            final MethodArgumentNotValidException methodArgumentNotValidException
    ) {
        return ErrorRepresentation.builder()
                .typeUri("/errors/client-errors/bad-requests/failed-validation")
                .title("Some validation failed")
                .failedValidations(failedValidationRepresentationFactory.createAll(methodArgumentNotValidException))
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRepresentation handleConstraintViolation(
            final HttpServletRequest httpServletRequest,
            final ConstraintViolationException constraintViolationException
    ) {
        return ErrorRepresentation.builder()
                .typeUri("/errors/client-errors/bad-requests/failed-validation")
                .title("Some validation failed")
                .failedValidations(failedValidationRepresentationFactory.createAll(constraintViolationException))
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRepresentation handleHttpMessageNotReadable(
            final HttpServletRequest httpServletRequest,
            final HttpMessageNotReadableException httpMessageNotReadableException
    ) {
        Throwable cause = httpMessageNotReadableException.getMostSpecificCause();
        List<FailedValidationRepresentation> failedValidations = switch (cause.getClass().getSimpleName()) {
            case "InvalidFormatException" ->
                    failedValidationRepresentationFactory.createAll((InvalidFormatException) cause);
            default -> {
                log.error("http message not readable for unknown cause", httpMessageNotReadableException);
                yield null;
            }
        };

        return ErrorRepresentation.builder()
                .typeUri("/errors/client-errors/bad-requests/failed-validation")
                .title("Some validation failed")
                .failedValidations(failedValidations)
                .build();
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorRepresentation handleAny(
            final HttpServletRequest httpServletRequest,
            final Throwable throwable
    ) {
        var message = "internal server error, endpoint={}".replace("{}", httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURI());
        log.error(message, throwable);

        return ErrorRepresentation.builder()
                .typeUri("/errors/server-errors/internal-server-error")
                .title("Something on our side went wrong")
                .build();
    }

    // TODO Add a handler for this HttpMediaTypeNotSupportedException

}
