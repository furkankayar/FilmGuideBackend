package com.service.filmguide.controller.movie;

import com.service.filmguide.controller.common.exception.MovieNotFoundException;
import com.service.filmguide.controller.common.exception.ReviewNotFoundException;
import com.service.filmguide.controller.movie.repository.MovieListProvider;
import com.service.filmguide.controller.movie.request.ReviewDAO;
import com.service.filmguide.controller.movie.response.MovieResponse;
import com.service.filmguide.controller.movie.service.MovieService;
import com.service.filmguide.themoviedb.dto.MovieListDTO;
import com.service.filmguide.themoviedb.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private APIService apiService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/trending")
    public MovieListDTO getTrendingMovies(@RequestParam(value = "page", required = false) Integer pageNumber){
        if(pageNumber == null){
            pageNumber = 1;
        }
        else if(pageNumber < 1 || pageNumber > 10){
            pageNumber = 1;
        }
        return movieService.getTrendingMovies(pageNumber);
    }

    @GetMapping("/top_rated")
    public MovieListDTO getTopRatedMovies(@RequestParam(value = "page", required = false) Integer pageNumber){
        if(pageNumber == null){
            pageNumber = 1;
        }
        else if(pageNumber < 1 || pageNumber > 10){
            pageNumber = 1;
        }
        return movieService.getTopRatedMovies(pageNumber);
    }

    @GetMapping("/upcoming")
    public MovieListDTO getUpComingMovies(@RequestParam(value = "page", required = false) Integer pageNumber){
        if(pageNumber == null){
            pageNumber = 1;
        }
        else if(pageNumber < 1 || pageNumber > 10){
            pageNumber = 1;
        }
        return movieService.getUpComingMovies(pageNumber);
    }

    @GetMapping("/now_playing")
    public MovieListDTO getNowPlayingMovies(@RequestParam(value = "page", required = false) Integer pageNumber){
        if(pageNumber == null){
            pageNumber = 1;
        }
        else if(pageNumber < 1 || pageNumber > 10){
            pageNumber = 1;
        }
        return movieService.getNowPlayingMovies(pageNumber);
    }

    @GetMapping("/search")
    public MovieListDTO getNowPlayingMovies(@RequestParam(value = "query", required = true) String query){
        return movieService.searchMovies(query);
    }

    @GetMapping("/{movie_id}")
    public MovieResponse getMovie(@PathVariable String movie_id){
        int movieId = 0;
        try{
            movieId = Integer.parseInt(movie_id);
        }catch (NumberFormatException ex){
            throw new MovieNotFoundException(movie_id);
        }
        return movieService.getMovie(movieId);
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

    @PostMapping("/{movie_id}/review")
    public ResponseEntity<Object> makeReview(@PathVariable String movie_id, @RequestBody @Valid ReviewDAO reviewDAO){

        int movieId = 0;
        try{
            movieId = Integer.parseInt(movie_id);
        } catch (NumberFormatException ex){
            throw new MovieNotFoundException(movieId);
        }

        return movieService.makeReview(movieId, reviewDAO);
    }

    @PostMapping("/{review_id}/like")
    public ResponseEntity<Object> likeReview(@PathVariable String review_id){
        int reviewId = 0;
        try{
            reviewId = Integer.parseInt(review_id);
        } catch (NumberFormatException ex){
            throw new ReviewNotFoundException(reviewId);
        }

        return movieService.likeReview(reviewId);
    }



    /*@GetMapping("/{movie_id}")
    public MovieResponse getMovie(@PathParam(value = "movie_id") Integer movieId){
        return
    }*/
}
