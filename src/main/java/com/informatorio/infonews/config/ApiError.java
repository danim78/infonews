package com.informatorio.infonews.config;

import org.springframework.http.HttpStatus;

public class ApiError {
    private HttpStatus status;
    private String message;
    private int ErrorCount;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCount() {
        return ErrorCount;
    }

    public void setErrorCount(int errorCount) {
        ErrorCount = errorCount;
    }
}
