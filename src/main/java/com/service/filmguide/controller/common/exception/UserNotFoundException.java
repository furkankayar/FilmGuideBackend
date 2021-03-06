package com.service.filmguide.controller.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(Long id) {
        super("User is not found with id: " + id);
    }

    public UserNotFoundException(String username) {
        super("User is not found with username: " + username);
    }
}