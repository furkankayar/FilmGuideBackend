package com.service.filmguide.controller.movie.service;

import com.service.filmguide.themoviedb.dto.PersonDTO;
import com.service.filmguide.themoviedb.dto.TrendDTO;
import com.service.filmguide.themoviedb.dto.TrendingTodayDTO;
import com.service.filmguide.model.Movie;
import com.service.filmguide.controller.movie.repository.IMovieRepository;
import com.service.filmguide.controller.movie.repository.ITrendingMoviesRepository;
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
    private ITrendingMoviesRepository trendingMoviesRepository;

    @Autowired
    private IMovieRepository movieRepository;

    @Async("threadPoolTaskExecutor")
    public void fetchAllMovieDataAsync(TrendingTodayDTO trendingTodayDTO){
        for(TrendDTO trendDTO : trendingTodayDTO.getResults()){
            Movie entityMovie = movieRepository.findById(trendDTO.getMovieId()).orElse(null);

            if(entityMovie == null || !entityMovie.isFullyFetched()){
                Movie movie = apiService.getMovie(trendDTO.getMovieId()).mapToMovie();
                movie.setFullyFetched(true);
                for(PersonDTO personDTO: apiService.getCast(movie.getMovieId()).getCast()){
                    movie.getCast().add(personDTO.mapToPerson());
                }
                for(VideoDTO videoDTO : apiService.getVideos(movie.getMovieId()).getResults()){
                    movie.getVideos().add(videoDTO.mapToVideo());
                }
                movieRepository.save(movie);
            }

            //trendingMoviesRepository.save(TrendingMovies.builder().pageNumber(trendingTodayDTO.getPage()).movie(movie).build());
        }

    }
}
