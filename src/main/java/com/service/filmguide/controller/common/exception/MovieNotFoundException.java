package com.service.filmguide.controller.common.exception;

public class MovieNotFoundException extends IllegalArgumentException{

    public MovieNotFoundException(int movieId){
        super("Movie not found with given id: " + movieId);
    }

    public MovieNotFoundException(String movieId){
        super("Movie not found with given id: " + movieId);
    }
}
