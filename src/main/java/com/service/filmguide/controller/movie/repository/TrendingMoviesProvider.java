package com.service.filmguide.controller.movie.repository;

import com.service.filmguide.controller.common.utility.CommonUtility;
import com.service.filmguide.model.Movie;
import com.service.filmguide.controller.movie.service.AsyncService;
import com.service.filmguide.model.User;
import com.service.filmguide.controller.user.repository.IUserRepository;
import com.service.filmguide.themoviedb.dto.TrendDTO;
import com.service.filmguide.themoviedb.dto.TrendingTodayDTO;
import com.service.filmguide.themoviedb.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrendingMoviesProvider {

    @Autowired
    private APIService apiService;

    @Autowired
    private ITrendingMoviesRepository trendingMoviesRepository;

    @Autowired
    private IMovieRepository movieRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private CommonUtility commonUtility;

    public TrendingTodayDTO getTrendingMovies(int page){

        User currentUser = commonUtility.getCurrentUser();
        List<Movie> movies = movieRepository.findAll();

        TrendingTodayDTO trendingTodayDTO = apiService.getTrendingMovies(page);
        asyncService.fetchAllMovieDataAsync(trendingTodayDTO);

        for(TrendDTO trendDTO : trendingTodayDTO.getResults()){
            trendDTO.setWatchlisted(false);
            for(Integer movie : currentUser.getWatchlist()){
                if(movie == trendDTO.getMovieId()){
                        trendDTO.setWatchlisted(true);
                }
            }

            //Movie movie = movieRepository.findById(trendDTO.getMovieId()).orElse(null);
            if(!movies.contains(trendDTO.mapToMovie())){
                movieRepository.save(trendDTO.mapToMovie());
            }

        }

        /*else{
            for(TrendingMovies trendingMovie : trendingMovies){
                TrendDTO trendDTO = trendingMovie.getMovie().mapToTrendDTO();
                trendDTO.setWatchlisted(false);
                for(Movie movie : currentUser.getWatchlist()){
                    if(movie.getMovieId() == trendDTO.getMovieId()){
                        trendDTO.setWatchlisted(true);
                    }
                }
                trendingTodayDTO.getResults().add(trendDTO);
            }
        }*/

        return trendingTodayDTO;
    }


}
