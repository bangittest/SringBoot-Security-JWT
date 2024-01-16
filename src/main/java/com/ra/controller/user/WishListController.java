package com.ra.controller.user;

import com.ra.dto.request.wishlist.WishListRequestDTO;
import com.ra.dto.respose.wishlist.WishListResponseDTO;
import com.ra.exception.ProductNotFoundExceptions;
import com.ra.exception.QuantityException;
import com.ra.exception.UserNotFoundException;
import com.ra.exception.WishListException;
import com.ra.security.principle.UserDetailService;
import com.ra.service.wishlist.WishListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class WishListController {
    @Autowired
    private UserDetailService userDetailsService;
    @Autowired
    private WishListService wishListService;
    @PostMapping("/wishlist")
    public ResponseEntity<?> addWishList(@RequestBody @Valid WishListRequestDTO wishListRequestDTO,
                                       Authentication authentication) throws UserNotFoundException, ProductNotFoundExceptions, WishListException, QuantityException {
        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = userDetailsService.getUserIdFromAuthentication(authentication);
            if (userId != null) {
                wishListService.addWishList(userId, wishListRequestDTO);
                return ResponseEntity.ok("Product added to the wishlist successfully");
            } else {
                return ResponseEntity.status(401).body("User ID not found in authentication details");
            }
        } else {
            return ResponseEntity.status(401).body("User not authenticated");
        }
    }

    @DeleteMapping("/wishlist/{id}")
    public ResponseEntity<?>deleteWishList(@PathVariable String id, Authentication authentication) throws UserNotFoundException, WishListException {
        try {
            Long idProduct= Long.valueOf(id);
            if (authentication != null && authentication.isAuthenticated()) {
                Long userId = userDetailsService.getUserIdFromAuthentication(authentication);
                if (userId != null) {
                    wishListService.deleteWishList(userId,idProduct);
                    return ResponseEntity.ok("Product delete to the wishlist successfully");
                } else {
                    return ResponseEntity.status(401).body("User ID not found in authentication details");
                }
            } else {
                return ResponseEntity.status(401).body("User not authenticated");
            }
        }catch (Exception e) {
            return new ResponseEntity<>("vui long nhap so error", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/wishlist")
    public ResponseEntity<?>deleteWishList( Authentication authentication) throws UserNotFoundException {
        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = userDetailsService.getUserIdFromAuthentication(authentication);
            if (userId != null) {
                List<WishListResponseDTO>list=wishListService.findByUser(userId);
                return ResponseEntity.ok(list);
            } else {
                return ResponseEntity.status(401).body("User ID not found in authentication details");
            }
        } else {
            return ResponseEntity.status(401).body("User not authenticated");
        }
    }
}
