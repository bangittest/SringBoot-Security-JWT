package com.ra.exception;

public class CartNotFoundException extends IllegalArgumentException {
    public CartNotFoundException(String cartItemNotFound) {
        super(cartItemNotFound);
    }
}
