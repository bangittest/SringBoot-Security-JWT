package com.ra.exception;

public class UserNotFoundException extends Throwable{
    public UserNotFoundException(String userNotFound) {
        super(userNotFound);
    }
}
