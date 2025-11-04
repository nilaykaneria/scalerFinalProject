package com.example.userauthenticationservice.controllers;

import com.example.userauthenticationservice.exceptions.IncorrectPasswordException;
import com.example.userauthenticationservice.exceptions.UserAlreadyExistsException;
import com.example.userauthenticationservice.exceptions.UserDoesnotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvisor {

    @ExceptionHandler({UserAlreadyExistsException.class, UserDoesnotExistException.class, IncorrectPasswordException.class})
    public ResponseEntity<String> handleAuthenticationException(Exception e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
