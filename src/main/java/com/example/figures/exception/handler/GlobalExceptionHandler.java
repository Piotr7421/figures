package com.example.figures.exception.handler;

import com.example.figures.exception.IncorrectParamsException;
import com.example.figures.exception.IncorrectParamsSizeException;
import com.example.figures.exception.IncorrectSearchKeyException;
import com.example.figures.exception.IncorrectSearchOperationException;
import com.example.figures.exception.NoMapperFoundException;
import com.example.figures.exception.model.ErrorResponse;
import com.example.figures.exception.model.ValidationError;
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
