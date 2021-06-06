package com.service.filmguide.controller.movie.repository;

import com.service.filmguide.controller.common.utility.CommonUtility;
import com.service.filmguide.model.Movie;
import com.service.filmguide.controller.movie.service.AsyncService;
import com.service.filmguide.model.User;
import com.service.filmguide.controller.user.repository.IUserRepository;
import com.service.filmguide.themoviedb.dto.MovieListItemDTO;
import com.service.filmguide.themoviedb.dto.MovieListDTO;
import com.service.filmguide.themoviedb.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieListProvider {

    @Autowired
    private APIService apiService;

    @Autowired
    private IMovieRepository movieRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private CommonUtility commonUtility;

    public MovieListDTO getTrendingMovies(int page){

        User currentUser = commonUtility.getCurrentUser();
        List<Movie> movies = movieRepository.findAll();

        MovieListDTO movieListDTO = apiService.getTrendingMovies(page);
        asyncService.fetchAllMovieDataAsync(movieListDTO);

        for(MovieListItemDTO movieListItemDTO : movieListDTO.getResults()){
            movieListItemDTO.setWatchlisted(false);
            for(Integer movie : currentUser.getWatchlist()){
                if(movie == movieListItemDTO.getMovieId()){
                        movieListItemDTO.setWatchlisted(true);
                }
            }

            if(!movies.contains(movieListItemDTO.mapToMovie())){
                movieRepository.save(movieListItemDTO.mapToMovie());
            }

        }

        return movieListDTO;
    }

    public MovieListDTO getTopRatedMovies(int page){

        User currentUser = commonUtility.getCurrentUser();
        List<Movie> movies = movieRepository.findAll();

        MovieListDTO movieListDTO = apiService.getTopRatedMovies(page);
        asyncService.fetchAllMovieDataAsync(movieListDTO);

        for(MovieListItemDTO movieListItemDTO : movieListDTO.getResults()){
            movieListItemDTO.setWatchlisted(currentUser.getWatchlist().contains(movieListItemDTO.getMovieId()));

            if(!movies.contains(movieListItemDTO.mapToMovie())){
                movieRepository.save(movieListItemDTO.mapToMovie());
            }
        }

        return movieListDTO;
    }


    public MovieListDTO getUpComingMovies(int page) {
        User currentUser = commonUtility.getCurrentUser();
        List<Movie> movies = movieRepository.findAll();

        MovieListDTO movieListDTO = apiService.getUpComingMovies(page);
        asyncService.fetchAllMovieDataAsync(movieListDTO);

        for(MovieListItemDTO movieListItemDTO : movieListDTO.getResults()){
            movieListItemDTO.setWatchlisted(currentUser.getWatchlist().contains(movieListItemDTO.getMovieId()));

            if(!movies.contains(movieListItemDTO.mapToMovie())){
                movieRepository.save(movieListItemDTO.mapToMovie());
            }
        }

        return movieListDTO;
    }

    public MovieListDTO getNowPlayingMovies(int page) {
        User currentUser = commonUtility.getCurrentUser();
        List<Movie> movies = movieRepository.findAll();

        MovieListDTO movieListDTO = apiService.getNowPlayingMovies(page);
        asyncService.fetchAllMovieDataAsync(movieListDTO);

        for(MovieListItemDTO movieListItemDTO : movieListDTO.getResults()){
            movieListItemDTO.setWatchlisted(currentUser.getWatchlist().contains(movieListItemDTO.getMovieId()));

            if(!movies.contains(movieListItemDTO.mapToMovie())){
                movieRepository.save(movieListItemDTO.mapToMovie());
            }
        }

        return movieListDTO;
    }

    public MovieListDTO searchMovies(String query) {
        User currentUser = commonUtility.getCurrentUser();
        List<Movie> movies = movieRepository.findAll();

        MovieListDTO movieListDTO = apiService.searchMovies(query);
        asyncService.fetchAllMovieDataAsync(movieListDTO);

        for(MovieListItemDTO movieListItemDTO : movieListDTO.getResults()){
            movieListItemDTO.setWatchlisted(currentUser.getWatchlist().contains(movieListItemDTO.getMovieId()));

            if(!movies.contains(movieListItemDTO.mapToMovie())){
                movieRepository.save(movieListItemDTO.mapToMovie());
            }
        }

        return movieListDTO;
    }
}
