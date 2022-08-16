package com.informatorio.infonews.config;

public class ApiSubError {
    private final String field;
    private final String message;

    public ApiSubError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
