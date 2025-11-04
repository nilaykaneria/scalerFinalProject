package com.example.userauthenticationservice.exceptions;

public class UserDoesnotExistException extends RuntimeException{
    public UserDoesnotExistException(String message) {
        super(message);
    }
}
