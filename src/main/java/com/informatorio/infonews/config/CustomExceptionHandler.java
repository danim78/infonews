package com.informatorio.infonews.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        ApiError error = new ApiError();
        error.setStatus(status);
        error.setMessage("Validation Error");
        error.setErrorCount(ex.getErrorCount());

        List<ApiSubError> subErrors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            subErrors.add(new ApiSubError(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        error.setSubErrors(subErrors);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
    ApiError error = new ApiError();
    error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    error.setMessage("Request Param Validation Error");
    error.setErrorCount(ex.getConstraintViolations().size());

    List<ApiSubError> subErrors = new ArrayList<>();
        for (ConstraintViolation constraintViolations : ex.getConstraintViolations()) {
            String field = null;
            for (Path.Node node : constraintViolations.getPropertyPath()) {
                field = node.getName();
            }
            subErrors.add(new ApiSubError(field, constraintViolations.getMessage()));
        }
    /*for (ConstraintViolation constraintViolations : ex.getConstraintViolations()){
        subErrors.add(new ApiSubError(constraintViolations.getPropertyPath().toString(), constraintViolations.getMessage()));
    }*/
    error.setSubErrors(subErrors);
    return new ResponseEntity<>(error, error.getStatus());
    }
}
