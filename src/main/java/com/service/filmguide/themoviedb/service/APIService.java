package com.service.filmguide.themoviedb.service;

import com.service.filmguide.themoviedb.Utility;
import com.service.filmguide.themoviedb.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class APIService {

    @Autowired
    private WebClient webClient;

    public MovieListDTO getTrendingMovies(int page){
        return webClient
                .get()
                .uri(Utility.buildUri("trending/movie/day", "page", page))
                .retrieve()
                .bodyToMono(MovieListDTO.class)
                .block();
    }

    public MovieListDTO getTopRatedMovies(int page){
        return webClient
                .get()
                .uri(Utility.buildUri("movie/top_rated", "page", page))
                .retrieve()
                .bodyToMono(MovieListDTO.class)
                .block();
    }

    public MovieListDTO getUpComingMovies(int page){
        return webClient
                .get()
                .uri(Utility.buildUri("movie/upcoming", "page", page))
                .retrieve()
                .bodyToMono(MovieListDTO.class)
                .block();
    }

    public MovieListDTO getNowPlayingMovies(int page){
        return webClient
                .get()
                .uri(Utility.buildUri("movie/now_playing", "page", page))
                .retrieve()
                .bodyToMono(MovieListDTO.class)
                .block();
    }

    public MovieListDTO searchMovies(String query){
        return webClient
                .get()
                .uri(Utility.buildUri("search/movie", "query", query))
                .retrieve()
                .bodyToMono(MovieListDTO.class)
                .block();

    }

    public MovieListDTO getSimilarMovies(int movieId){
        return webClient
                .get()
                .uri(Utility.buildUri("movie/" + movieId + "/similar"))
                .retrieve()
                .bodyToMono(MovieListDTO.class)
                .block();
    }

    public MovieDTO getMovie(int movieId){
        return webClient
                .get()
                .uri(Utility.buildUri("movie/" + movieId))
                .retrieve()
                .bodyToMono(MovieDTO.class)
                .block();
    }

    public MovieCreditsDTO getCast(int movieId){
        return webClient
                .get()
                .uri(Utility.buildUri("movie/" + movieId + "/credits"))
                .retrieve()
                .bodyToMono(MovieCreditsDTO.class)
                .block();
    }

    public VideosDTO getVideos(int movieId){
        return webClient
                .get()
                .uri(Utility.buildUri("movie/" + movieId + "/videos"))
                .retrieve()
                .bodyToMono(VideosDTO.class)
                .block();
    }

}
