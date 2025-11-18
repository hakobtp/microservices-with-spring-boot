package com.htp.microservices.api.exceptions;

public class InvalidRequestException extends BaseException {

    public InvalidRequestException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}