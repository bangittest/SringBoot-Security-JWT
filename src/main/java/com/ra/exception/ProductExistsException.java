package com.ra.exception;


public class ProductExistsException extends Throwable {
    public ProductExistsException(String message) {
        super(message);
    }
}
