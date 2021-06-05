package com.service.filmguide.controller.common.exception;

public class ReviewNotFoundException extends IllegalArgumentException{

    public ReviewNotFoundException(int movieId){
        super("Review not found with given id: " + movieId);
    }

    public ReviewNotFoundException(String movieId){
        super("Review not found with given id: " + movieId);
    }
}