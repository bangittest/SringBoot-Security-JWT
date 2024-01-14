package com.ra.exception;

public class ProductNotFoundExceptions extends RuntimeException{
    public ProductNotFoundExceptions(String roleNotFound) {
        super(roleNotFound);
    }
}
