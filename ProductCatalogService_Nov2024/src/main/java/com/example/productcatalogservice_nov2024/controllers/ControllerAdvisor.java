package com.example.productcatalogservice_nov2024.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice // we add this when we want to handel exception which is thrown from any controller
public class ControllerAdvisor {

    //BASICALLY THIS CLASS IS FOR EXCEPTION HANDLING FROM ANY OF THE CONTROLLER
    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<String> handelException(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

    }

//    @ExceptionHandler(HttpClientErrorException.NotFound.class)
//    public ResponseEntity<String> handelException(Exception exception) {
//        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
//    }
}
