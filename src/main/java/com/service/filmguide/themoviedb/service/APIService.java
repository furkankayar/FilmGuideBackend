package com.service.filmguide.themoviedb.service;

import com.service.filmguide.themoviedb.Utility;
import com.service.filmguide.themoviedb.dto.MovieCreditsDTO;
import com.service.filmguide.themoviedb.dto.MovieDTO;
import com.service.filmguide.themoviedb.dto.TrendingTodayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class APIService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public TrendingTodayDTO getTrendingMovies(int page){
        return webClientBuilder.build()
                .get()
                .uri(Utility.buildUri("trending/movie/day", "page", page))
                .retrieve()
                .bodyToMono(TrendingTodayDTO.class)
                .block();
    }

    public MovieDTO getMovie(int movieId){
        return webClientBuilder.build()
                .get()
                .uri(Utility.buildUri("movie/" + movieId))
                .retrieve()
                .bodyToMono(MovieDTO.class)
                .block();
    }

    public MovieCreditsDTO getCast(int movieId){
        return webClientBuilder.build()
                .get()
                .uri(Utility.buildUri("movie/" + movieId + "/credits"))
                .retrieve()
                .bodyToMono(MovieCreditsDTO.class)
                .block();
    }


}
