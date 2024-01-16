package com.ra.service.cart;

import com.ra.dto.request.cart.AddToCartRequestDTO;
import com.ra.dto.request.cart.CartRequestDTO;
import com.ra.dto.respose.cart.CartResponseDTO;
import com.ra.exception.CartEmptyException;
import com.ra.exception.ProductIDNotFoundException;
import com.ra.exception.QuantityException;
import com.ra.exception.UserNotFoundException;


import java.util.List;

public interface CartService {
    List<CartResponseDTO>findAllByUser(Long userId) throws UserNotFoundException;
    void addToCart(Long userId, AddToCartRequestDTO addToCartRequestDTO) throws UserNotFoundException, QuantityException;
    void updateProductQuantity(Long userId, CartRequestDTO cartRequestDTO) throws ProductIDNotFoundException, QuantityException;
    void deleteCartItem(Long userId, Long cartItemId) throws UserNotFoundException;
    void checkout(Long userId) throws UserNotFoundException, CartEmptyException;
}
