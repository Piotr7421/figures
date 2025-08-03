package io.github.Piotr7421.figures.exception.handler;

import io.github.Piotr7421.figures.exception.IncorrectParamsException;
import io.github.Piotr7421.figures.exception.IncorrectParamsSizeException;
import io.github.Piotr7421.figures.exception.IncorrectSearchKeyException;
import io.github.Piotr7421.figures.exception.IncorrectSearchOperationException;
import io.github.Piotr7421.figures.exception.NoMapperFoundException;
import io.github.Piotr7421.figures.exception.model.ErrorResponse;
import io.github.Piotr7421.figures.exception.model.ValidationError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationError handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ValidationError errorDto = new ValidationError();
        exception.getFieldErrors().forEach(fieldError ->
                errorDto.addViolation(fieldError.getField(), fieldError.getDefaultMessage()));
        return errorDto;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationError handleConstraintViolationException(ConstraintViolationException exception) {
        ValidationError validationError = new ValidationError();
        exception.getConstraintViolations().forEach(constraintViolation ->
                validationError.addViolation(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));
        return validationError;
    }

    @ExceptionHandler({
            NoMapperFoundException.class,
            IncorrectSearchKeyException.class,
            IncorrectSearchOperationException.class,
            IncorrectParamsSizeException.class,
            IncorrectParamsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleSpecifiedException(RuntimeException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
