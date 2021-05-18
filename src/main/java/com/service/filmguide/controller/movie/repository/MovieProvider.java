package com.service.filmguide.controller.movie.repository;

import com.service.filmguide.controller.movie.service.AsyncService;
import com.service.filmguide.themoviedb.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieProvider {

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private IMovieRepository movieRepository;

    @Autowired
    private APIService apiService;

    /*public MovieResponse getMovie(int movieId){

        Movie movie = movieRepository.findById(movieId).orElse(null);

        if(movie == null || ){

        }

        return movie.mapToMovieResponse();
    }*/
}
