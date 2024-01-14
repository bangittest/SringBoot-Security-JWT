package com.ra.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class YourCustomBadRequestException extends RuntimeException {

    private final String errorCode;

    public YourCustomBadRequestException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}