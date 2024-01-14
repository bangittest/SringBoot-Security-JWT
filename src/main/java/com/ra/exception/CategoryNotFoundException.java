package com.ra.exception;

public class CategoryNotFoundException extends RuntimeException  {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
