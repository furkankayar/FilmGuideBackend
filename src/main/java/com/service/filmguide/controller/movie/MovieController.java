package com.service.filmguide.controller.movie;

import com.service.filmguide.controller.common.exception.MovieNotFoundException;
import com.service.filmguide.controller.movie.repository.TrendingMoviesProvider;
import com.service.filmguide.controller.movie.service.MovieService;
import com.service.filmguide.themoviedb.dto.TrendingTodayDTO;
import com.service.filmguide.themoviedb.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private APIService apiService;

    @Autowired
    private TrendingMoviesProvider trendingMoviesProvider;

    @Autowired
    private MovieService movieService;

    @GetMapping("/trending")
    public TrendingTodayDTO getTrendingMovies(@RequestParam(value = "page", required = false) Integer pageNumber){
        if(pageNumber == null){
            pageNumber = 1;
        }
        else if(pageNumber < 1 || pageNumber > 10){
            pageNumber = 1;
        }
        return trendingMoviesProvider.getTrendingMovies(pageNumber);
    }

    @PostMapping("/{movie_id}/addWatchlist")
    public ResponseEntity<Object> addMovieToWatchlist(@PathVariable String movie_id){
        int movieId = 0;
        try{
            movieId = Integer.parseInt(movie_id);
        }catch (NumberFormatException ex){
            throw new MovieNotFoundException(movie_id);
        }
        return movieService.addMovieToWatchlist(movieId);
    }

    @PostMapping("/{movie_id}/removeWatchlist")
    public ResponseEntity<Object> removeFromWatchlist(@PathVariable String movie_id){
        int movieId = 0;
        try{
            movieId = Integer.parseInt(movie_id);
        } catch (NumberFormatException ex){
            throw new MovieNotFoundException(movieId);
        }
        return movieService.removeFromWatchlist(movieId);
    }

    /*@GetMapping("/{movie_id}")
    public MovieResponse getMovie(@PathParam(value = "movie_id") Integer movieId){
        return
    }*/
}
