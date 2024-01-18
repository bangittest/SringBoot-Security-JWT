package com.ra.advice;

import com.ra.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> invalidRequest(MethodArgumentNotValidException e){
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> customException(CustomException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<String>categoryAlreadyExists(CategoryAlreadyExistsException category){
        return new ResponseEntity<>(category.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CartEmptyException.class)
    public ResponseEntity<String>cartEmptyException(CartEmptyException c){
        return new ResponseEntity<>(c.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<String>CartNotFoundException(CartNotFoundException c){
        return new ResponseEntity<>(c.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String>ProductNotFoundException(ProductNotFoundException c){
        return new ResponseEntity<>(c.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String>UserNotFoundException(UserNotFoundException c){
        return new ResponseEntity<>(c.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RoleNotFoundExceptions.class)
    public ResponseEntity<String> RoleNotFoundExceptions(RoleNotFoundExceptions ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserRoleNotFoundExceptionss.class)
    public ResponseEntity<String> UserRoleNotFoundExceptionss(UserRoleNotFoundExceptionss ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductNotFoundExceptions.class)
    public ResponseEntity<String> ProductNotFoundExceptions(ProductNotFoundExceptions ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> OrderNotFoundException(OrderNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(WishListException.class)
    public ResponseEntity<String> WishListException(WishListException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ColorExceptionNotFound.class)
    public ResponseEntity<String> ColorExceptionNotFound(ColorExceptionNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SizeNotFoundException.class)
    public ResponseEntity<String> SizeNotFoundException(SizeNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductExistsException.class)
    public ResponseEntity<String> ProductExistsException(ProductExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductIDNotFoundException.class)
    public ResponseEntity<String> ProductIDNotFoundException(ProductIDNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NO_CONTENT);
    }
    @ExceptionHandler(QuantityException.class)
    public ResponseEntity<String> QuantityException(QuantityException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }





}
