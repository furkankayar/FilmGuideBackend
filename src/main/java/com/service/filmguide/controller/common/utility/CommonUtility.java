package com.service.filmguide.controller.common.utility;

import com.service.filmguide.controller.common.error.ApiError;
import com.service.filmguide.controller.user.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommonUtility {

    public User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User){
            return (User)principal;
        } else {
            return null;
        }
    }

    public ResponseEntity<Object> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    public ResponseEntity<Object> buildResponse(Map<String, Object> map, HttpStatus status){
        return new ResponseEntity<>(map, status);
    }
}
