package com.ra.exception;

import org.springframework.http.HttpStatus;

public class QuantityException extends Throwable {
    public QuantityException(String applicationContextError, HttpStatus httpStatus) {
        super(applicationContextError);
    }
}
