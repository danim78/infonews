package com.informatorio.infonews.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
}
