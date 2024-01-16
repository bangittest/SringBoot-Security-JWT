package com.ra.exception;

public class ProductIDNotFoundException extends Throwable {
    public ProductIDNotFoundException(String productNotFound) {
        super(productNotFound);
    }
}
