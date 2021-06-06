package com.service.filmguide.controller.movie.service;

import com.service.filmguide.themoviedb.dto.PersonDTO;
import com.service.filmguide.themoviedb.dto.MovieListItemDTO;
import com.service.filmguide.themoviedb.dto.MovieListDTO;
import com.service.filmguide.model.Movie;
import com.service.filmguide.controller.movie.repository.IMovieRepository;
import com.service.filmguide.themoviedb.dto.VideoDTO;
import com.service.filmguide.themoviedb.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Autowired
    private APIService apiService;

    @Autowired
    private IMovieRepository movieRepository;

    @Async("threadPoolTaskExecutor")
    public void fetchAllMovieDataAsync(MovieListDTO movieListDTO){
        for(MovieListItemDTO movieListItemDTO : movieListDTO.getResults()){
            Movie entityMovie = movieRepository.findById(movieListItemDTO.getMovieId()).orElse(null);

            if(entityMovie == null || !entityMovie.isFullyFetched()){
                Movie movie = apiService.getMovie(movieListItemDTO.getMovieId()).mapToMovie();
                movie.setFullyFetched(true);
                for(PersonDTO personDTO: apiService.getCast(movie.getMovieId()).getCast()){
                    movie.getCast().add(personDTO.mapToPerson());
                }
                for(VideoDTO videoDTO : apiService.getVideos(movie.getMovieId()).getResults()){
                    movie.getVideos().add(videoDTO.mapToVideo());
                }
                movieRepository.save(movie);
            }
        }

    }
}
