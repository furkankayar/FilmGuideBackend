package com.service.filmguide.controller.common.advice;

import com.service.filmguide.controller.common.error.ApiError;
import com.service.filmguide.controller.common.exception.NotProperIdException;
import com.service.filmguide.controller.common.exception.UserNotFoundException;

import com.service.filmguide.controller.common.utility.CommonUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserAdvice {

    @Autowired
    private CommonUtility utility;
    
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        String error = "Requested data is not found!";
        return utility.buildErrorResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, ex));
    }

    @ExceptionHandler(value = NotProperIdException.class)
    public ResponseEntity<Object> handleNotProperIdException(NotProperIdException ex) {
        String error = "Id have to be long!";
        return utility.buildErrorResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }
}