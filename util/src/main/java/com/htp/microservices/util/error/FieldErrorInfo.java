package com.htp.microservices.util.error;

import org.springframework.validation.FieldError;


public record FieldErrorInfo(String fieldName, String message) {

    /**
     * Converts a Spring FieldError into our FieldErrorInfo record.
     */
    public static FieldErrorInfo from(FieldError fieldError) {
        return new FieldErrorInfo(
                fieldError.getField(),
                fieldError.getDefaultMessage()
        );
    }
}