package com.ra.controller.user;

import com.ra.dto.request.user.UserProfileDTO;
import com.ra.dto.respose.user.UserDTO;
import com.ra.exception.CustomException;
import com.ra.exception.UserNotFoundException;
import com.ra.security.principle.UserDetailService;
import com.ra.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class CustomerController {
    @Autowired
    private UserDetailService userDetailsService;
    @Autowired
    private UserService userService;
    @GetMapping("/account")
    public ResponseEntity<?>account(Authentication authentication) throws UserNotFoundException {
        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = userDetailsService.getUserIdFromAuthentication(authentication);
            if (userId != null) {
                    UserDTO userDTO=userService.checkById(userId);
                return new  ResponseEntity(userDTO, HttpStatus.OK);
            } else {
                return ResponseEntity.status(401).body("User ID not found in authentication details");
            }
        } else {
            return ResponseEntity.status(401).body("User not authenticated");
        }
    }
    @PutMapping("/account")
    public ResponseEntity<?>update(@RequestBody UserProfileDTO userProfileDTO, Authentication authentication) throws UserNotFoundException, CustomException {
        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = userDetailsService.getUserIdFromAuthentication(authentication);
            if (userId != null) {
                UserDTO userNew= userService.updateProfile(userId,userProfileDTO);
                return new  ResponseEntity(userNew, HttpStatus.OK);
            } else {
                return ResponseEntity.status(401).body("User ID not found in authentication details");
            }
        } else {
            return ResponseEntity.status(401).body("User not authenticated");
        }
    }
    @PutMapping("/account/change-password")
    public ResponseEntity<?>updatePassword(@RequestParam(name = "password") String password,Authentication authentication) throws UserNotFoundException {
        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = userDetailsService.getUserIdFromAuthentication(authentication);
            if (userId != null) {
                 userService.updatePassword(userId,password);
                return new  ResponseEntity("doi mat khau thanh cong", HttpStatus.OK);
            } else {
                return ResponseEntity.status(401).body("User ID not found in authentication details");
            }
        } else {
            return ResponseEntity.status(401).body("User not authenticated");
        }
    }
}
