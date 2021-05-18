package com.service.filmguide.controller.movie.service;


import com.service.filmguide.controller.common.exception.MovieNotFoundException;
import com.service.filmguide.controller.common.utility.CommonUtility;
import com.service.filmguide.controller.movie.model.Movie;
import com.service.filmguide.controller.movie.repository.IMovieRepository;
import com.service.filmguide.controller.user.repository.IUserRepository;
import com.service.filmguide.controller.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.filmguide.controller.user.model.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class MovieService {

    @Autowired
    private CommonUtility commonUtility;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IMovieRepository movieRepository;

    public ResponseEntity<Object> addMovieToWatchlist(int movieId){
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));

        User user = commonUtility.getCurrentUser();

        user.getWatchlist().add(movie);

        userRepository.save(user);

        Map<String, Object> map = new HashMap<>();

        map.put("success", true);

        return commonUtility.buildResponse(map, HttpStatus.OK);
    }
}
