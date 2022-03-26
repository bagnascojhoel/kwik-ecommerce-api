package br.com.kwikecommerce.api.application.exceptionhandling;

import br.com.kwikecommerce.api.application.exceptionhandling.FieldValidationResponseDto.FieldValidation;
import br.com.kwikecommerce.api.application.logging.LogService;
import br.com.kwikecommerce.api.application.message.MessageProperty;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestControllerAdvice
public record ExceptionControllerAdvice(LogService logService) {

    // 4xx

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldValidationResponseDto handleFieldValidations(BindException ex) {
        return this.createFieldValidationResponse(ex.getAllErrors());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldValidationResponseDto handleFieldValidations(ConstraintViolationException ex) {
        return createFieldValidationResponse(ex.getConstraintViolations());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponseDto handleNotFound(BadRequestException ex) {
        return createGenericExceptionResponse(ex.getTextForClient());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponseDto handleNotFound(NotFoundException ex) {
        return createGenericExceptionResponse(ex.getTextForClient());
    }

    // 5xx

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponseDto handleInternalServerException(
        InternalServerException internalServerException
    ) {
        logService.logError(internalServerException.getLogMessage().getText(), internalServerException.getCause());
        return createGenericExceptionResponse(internalServerException.getTextForClient());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponseDto handleAnyException(Exception ex) {
        logService.logError(MessageProperty.use("log.unknown-error"), ex);
        return createGenericExceptionResponse(MessageProperty.of("e.unknown"));
    }

    private ExceptionResponseDto createGenericExceptionResponse(MessageProperty messageProperty) {
        return ExceptionResponseDto.builder()
            .message(messageProperty.getText())
            .build();
    }

    private FieldValidationResponseDto createFieldValidationResponse(Set<ConstraintViolation<?>> constraintViolations) {
        var validations = new ArrayList<FieldValidation>();

        for (var constraintViolation : constraintViolations) {
            var value = Optional.ofNullable(constraintViolation.getInvalidValue())
                .map(Object::toString)
                .orElse(null);
            var message = getValidationMessage(constraintViolation);

            validations.add(FieldValidation.builder()
                .field(constraintViolation.getPropertyPath().toString())
                .value(value)
                .message(message)
                .build());
        }

        return FieldValidationResponseDto.builder()
            .validations(validations)
            .build();
    }

    private FieldValidationResponseDto createFieldValidationResponse(List<ObjectError> objectErrors) {
        var validations = new ArrayList<FieldValidation>();

        for (var objectError : objectErrors) {
            if (objectError instanceof FieldError) {
                var fieldValidation = this.toFieldValidation((FieldError) objectError);
                validations.add(fieldValidation);
            }
        }

        return FieldValidationResponseDto.builder()
            .validations(validations)
            .build();
    }

    private FieldValidation toFieldValidation(FieldError fieldError) {
        var value = Optional.ofNullable(fieldError.getRejectedValue())
            .map(Object::toString)
            .orElse(null);
        var message = this.extractMessage(fieldError);

        return FieldValidation.builder()
            .field(fieldError.getField())
            .value(value)
            .message(message)
            .build();
    }

    private String extractMessage(FieldError fieldError) {
        var result = MessageProperty.use("e.exception-controller-advice.invalid-value-binded");

        try {
            var rootCause = fieldError.unwrap(TypeMismatchException.class).getRootCause();
            if (rootCause instanceof InternalServerException) {
                var internalServerException = ((InternalServerException) rootCause);
                logService.logError(internalServerException.getLogMessage().getText());
                result = internalServerException.getTextForClient().getText();
            }
        } catch (IllegalArgumentException ignored) {
        }


        return result;
    }

    private String getValidationMessage(ConstraintViolation<?> constraintViolation) {
        var messageKey = constraintViolation.getMessageTemplate().replace("{", "").replace("}", "");

        String message;
        try {
            message = MessageProperty.use(messageKey);
        } catch (NoSuchMessageException ex) {
            message = constraintViolation.getMessage();
        }

        return message;
    }

}
