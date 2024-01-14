package com.ra.controller;

import com.ra.dto.request.UserRequestDTO;
import com.ra.dto.request.user.UserRegisterRequestDTO;
import com.ra.dto.respose.UserResponseDTO;
import com.ra.exception.CustomException;
import com.ra.exception.UserNotFoundException;
import com.ra.model.User;
import com.ra.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody @Valid UserRegisterRequestDTO userRegisterRequestDTO)throws CustomException {
        return new ResponseEntity<>(userService.register(userRegisterRequestDTO), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody @Valid UserRequestDTO userRequestDTO) throws UserNotFoundException {
        UserResponseDTO userResponseDTO=userService.login(userRequestDTO);
        return new ResponseEntity<>(userResponseDTO,HttpStatus.OK);
    }
}
