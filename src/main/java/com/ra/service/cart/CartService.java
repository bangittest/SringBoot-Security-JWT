package com.ra.service.cart;

import com.ra.dto.request.cart.CartRequestDTO;
import com.ra.dto.request.cart.AddToCartRequestDTO;
import com.ra.exception.CartEmptyException;
import com.ra.exception.UserNotFoundException;

public interface CartService {
    void addToCart(Long userId, AddToCartRequestDTO addToCartRequestDTO) throws UserNotFoundException;
    void updateProductQuantity(Long userId,CartRequestDTO cartRequestDTO) throws UserNotFoundException;
    void deleteCartItem(Long userId, Long cartItemId) throws UserNotFoundException;
    void checkout(Long userId) throws UserNotFoundException, CartEmptyException;
}
