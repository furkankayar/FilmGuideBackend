package com.service.filmguide.controller.common.advice;

import com.service.filmguide.controller.common.error.ApiError;
import com.service.filmguide.controller.common.exception.MovieNotFoundException;
import com.service.filmguide.controller.common.utility.CommonUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MovieAdvice {

    @Autowired
    private CommonUtility utility;

    @ExceptionHandler(value = MovieNotFoundException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MovieNotFoundException ex){
        List<String> errors = new ArrayList<String>();

        if(ex.getMessage() != null) {
            errors.add(ex.getMessage());
        }
        else{
            errors.add("An error occurred!");
        }
        return utility.buildErrorResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, errors, ex));
    }

}
