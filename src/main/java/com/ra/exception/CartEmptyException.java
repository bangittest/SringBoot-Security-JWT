package com.ra.exception;

public class CartEmptyException extends Throwable {
    public CartEmptyException(String shoppingCartIsEmpty) {
        super(shoppingCartIsEmpty);
    }
}
