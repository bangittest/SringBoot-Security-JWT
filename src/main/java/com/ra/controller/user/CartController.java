package com.ra.controller.user;

import com.ra.dto.request.cart.CartRequestDTO;
import com.ra.dto.request.cart.AddToCartRequestDTO;
import com.ra.dto.respose.orders.OrderDetailResponseDTO;
import com.ra.dto.respose.orders.OrderResponseDTO;
import com.ra.exception.*;
import com.ra.model.Orders;
import com.ra.security.principle.UserDetailService;
import com.ra.service.cart.CartService;
import com.ra.service.order.OrderService;
import com.ra.service.orderdetail.OrderDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserDetailService userDetailsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/cart")
    public ResponseEntity<?> addToCart(@RequestBody @Valid AddToCartRequestDTO addToCartRequestDTO,
            Authentication authentication) throws UserNotFoundException, ProductNotFoundExceptions {
           if (authentication != null && authentication.isAuthenticated()) {
               Long userId = userDetailsService.getUserIdFromAuthentication(authentication);
               if (userId != null) {
                   cartService.addToCart(userId, addToCartRequestDTO);
                   return ResponseEntity.ok("Product added to the cart successfully");
               } else {
                   return ResponseEntity.status(401).body("User ID not found in authentication details");
               }
           } else {
               return ResponseEntity.status(401).body("User not authenticated");
           }
    }
    @PutMapping("/cart")
    public ResponseEntity<?>updateCart(@RequestBody @Valid CartRequestDTO cartRequestDTO,
                                       Authentication authentication) throws UserNotFoundException,ProductNotFoundExceptions {
            if (authentication != null && authentication.isAuthenticated()) {
                Long userId = userDetailsService.getUserIdFromAuthentication(authentication);
                if (userId != null) {
                    cartService.updateProductQuantity(userId,cartRequestDTO);
                    return ResponseEntity.ok("Product update to the cart successfully");
                } else {
                    return ResponseEntity.status(401).body("User ID not found in authentication details");
                }
            } else {
                return ResponseEntity.status(401).body("User not authenticated");
            }
    }
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<?>deleteCart(@PathVariable Long id, Authentication authentication) throws UserNotFoundException {
            if (authentication != null && authentication.isAuthenticated()) {
                Long userId = userDetailsService.getUserIdFromAuthentication(authentication);
                if (userId != null) {
                    cartService.deleteCartItem(userId,id);
                    return ResponseEntity.ok("Product delete to the cart successfully");
                } else {
                    return ResponseEntity.status(401).body("User ID not found in authentication details");
                }
            } else {
                return ResponseEntity.status(401).body("User not authenticated");
            }
    }
    @PostMapping("/cart/checkout")
    public ResponseEntity<?>checkOut(Authentication authentication) throws UserNotFoundException, CartEmptyException {
            if (authentication != null && authentication.isAuthenticated()) {
                Long userId = userDetailsService.getUserIdFromAuthentication(authentication);
                if (userId != null) {
                    cartService.checkout(userId);
                    return ResponseEntity.ok("Product checkOut to the cart successfully");
                } else {
                    return ResponseEntity.status(401).body("User ID not found in authentication details");
                }
            } else {
                return ResponseEntity.status(401).body("User not authenticated");
            }
    }
    @GetMapping("/history")
    public ResponseEntity<?>history(Authentication authentication) throws UserNotFoundException {
        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = userDetailsService.getUserIdFromAuthentication(authentication);
            if (userId != null) {
                List<OrderResponseDTO> orders=orderService.findAllById(userId);
                return new  ResponseEntity(orders, HttpStatus.OK);
            } else {
                return ResponseEntity.status(401).body("User ID not found in authentication details");
            }
        } else {
            return ResponseEntity.status(401).body("User not authenticated");
        }
    }
    @GetMapping("/history/{orderId}")
    public ResponseEntity<?>findAllOrderId(@PathVariable Long orderId,Authentication authentication) throws OrderNotFoundException, UserNotFoundException {

        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = userDetailsService.getUserIdFromAuthentication(authentication);
            if (userId != null) {
                List<OrderResponseDTO>list=orderService.findAllByOrderId(userId,orderId);
                return new  ResponseEntity(list, HttpStatus.OK);
            } else {
                return ResponseEntity.status(401).body("User ID not found in authentication details");
            }
        } else {
            return ResponseEntity.status(401).body("User not authenticated");
        }
    }
    @PatchMapping("/history/{orderId}/cancel")
    public ResponseEntity<?>cancel(@PathVariable("orderId") Long orderId) throws OrderNotFoundException {
        OrderResponseDTO orderResponseDTO=orderService.updateStatus(orderId,2);
        return new ResponseEntity<>(orderResponseDTO,HttpStatus.OK);
    }
}
