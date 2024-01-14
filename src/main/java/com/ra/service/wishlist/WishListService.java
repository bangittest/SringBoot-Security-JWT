package com.ra.service.wishlist;

import com.ra.dto.request.wishlist.WishListRequestDTO;
import com.ra.dto.respose.wishlist.WishListResponseDTO;
import com.ra.exception.UserNotFoundException;
import com.ra.exception.WishListException;

import java.util.List;

public interface WishListService {
    void addWishList(Long userId, WishListRequestDTO wishListRequestDTO) throws UserNotFoundException, WishListException;
    void deleteWishList(Long userId, Long cartItemId) throws UserNotFoundException, WishListException;
    List<WishListResponseDTO> findByUser(Long userId) throws UserNotFoundException;
}
