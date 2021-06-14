package com.service.filmguide.controller.movie.service;


import com.service.filmguide.controller.common.exception.MovieNotFoundException;
import com.service.filmguide.controller.common.exception.ReviewNotFoundException;
import com.service.filmguide.controller.common.exception.UserNotFoundException;
import com.service.filmguide.controller.common.utility.CommonUtility;
import com.service.filmguide.controller.movie.repository.IMovieRepository;
import com.service.filmguide.controller.movie.repository.IReviewRepository;
import com.service.filmguide.controller.movie.repository.MovieListProvider;
import com.service.filmguide.controller.movie.request.ReviewDAO;
import com.service.filmguide.controller.movie.response.GenreResponse;
import com.service.filmguide.controller.movie.response.MovieResponse;
import com.service.filmguide.controller.movie.response.ReviewResponse;
import com.service.filmguide.controller.user.repository.IUserRepository;
import com.service.filmguide.model.*;
import com.service.filmguide.themoviedb.dto.MovieListDTO;
import com.service.filmguide.themoviedb.dto.MovieListItemDTO;
import com.service.filmguide.themoviedb.dto.PersonDTO;
import com.service.filmguide.themoviedb.dto.VideoDTO;
import com.service.filmguide.themoviedb.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieService {

    @Autowired
    private CommonUtility commonUtility;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IMovieRepository movieRepository;

    @Autowired
    private IReviewRepository reviewRepository;

    @Autowired
    private APIService apiService;

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private MovieListProvider movieListProvider;

    public MovieResponse getMovie(int movieId){

        User user = commonUtility.getCurrentUser();
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if(movie == null){
            movie = apiService.getMovie(movieId).mapToMovie();
            if(movie == null){
                return MovieResponse.builder().build();
            } else {
                movie.setFullyFetched(true);
                for(PersonDTO personDTO: apiService.getCast(movie.getMovieId()).getCast()){
                    movie.getCast().add(personDTO.mapToPerson());
                }
                for(VideoDTO videoDTO : apiService.getVideos(movie.getMovieId()).getResults()){
                    movie.getVideos().add(videoDTO.mapToVideo());
                }
                movieRepository.save(movie);
                movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
            }
        }


        List<MovieListItemDTO> similarMovies;
        if(movie.getSimilarMovies().isEmpty()){
            MovieListDTO similarMoviesResponse = apiService.getSimilarMovies(movieId);
            similarMovies = similarMoviesResponse.getResults();
            List<Integer> similarMovieIds = new ArrayList<>();
            for(MovieListItemDTO movieListItemDTO : similarMoviesResponse.getResults()){
                similarMovieIds.add(movieListItemDTO.getMovieId());
            }
            movie.setSimilarMovies(new HashSet<>(similarMovieIds));
            movieRepository.save(movie);
            asyncService.fetchAllMovieDataAsync(similarMoviesResponse);
        }
        else{
            similarMovies = new ArrayList<>();
            for(int id : movie.getSimilarMovies()){
                movieRepository.findById(id).ifPresent(similarMovie -> similarMovies.add(similarMovie.mapToTrendDTO()));
            }
        }

        boolean watchlisted = false;
        for(Integer m : user.getWatchlist()){
            if (m.equals(movie.getMovieId())) {
                watchlisted = true;
                break;
            }
        }
        List<GenreResponse> genres = new ArrayList<>();
        for(Genre genre : movie.getGenres()){
            if(!genres.contains(genre)){
                genres.add(GenreResponse.builder().name(genre.getName()).id(genre.getId()).build());
            }
        }

        List<ReviewResponse> reviews = new ArrayList<>();
        List<Review> movieReviews = new ArrayList<>(movie.getReviews());
        movieReviews.sort(Comparator.comparing(Review::getDate));
        for(Review review : movieReviews){
            User reviewUser = userRepository.findById(review.getUserId()).orElseThrow(()->new UserNotFoundException(review.getUserId()));
            reviews.add(ReviewResponse.builder()
                    .content(review.getContent())
                    .date(review.getDate())
                    .id(review.getId())
                    .likeCount(review.getLikeCount())
                    .rating(review.getRating())
                    .user(reviewUser)
                    .title(review.getTitle())
                    .liked(user.getLikedReviews().contains(review.getId()))
                    .build());
        }

        List<Person> castSorted = new ArrayList<>(movie.getCast());
        castSorted.sort((o1, o2) -> o2.getPopularity().compareTo(o1.getPopularity()));

        List<Person> castFiltered = new ArrayList<>();
        for(Person person : castSorted){
            if(!castFiltered.contains(person)){
                castFiltered.add(person);
            }
        }

        return MovieResponse.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .releaseDate(movie.getReleaseDate())
                .overview(movie.getOverview())
                .posterPath(movie.getPosterUrl())
                .backdropPath(movie.getBackdropUrl())
                .tagline(movie.getTagline())
                .rate(movie.getRate())
                .lang(movie.getLang())
                .genres(genres)
                .spokenLanguages(new ArrayList<>(movie.getSpokenLanguages()))
                .cast(castFiltered)
                .videos(new ArrayList<>(movie.getVideos()))
                .reviews(reviews)
                .similarMovies(similarMovies)
                .watchlisted(watchlisted)
                .build();
    }

    public ResponseEntity<Object> addMovieToWatchlist(int movieId){
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
        User user = commonUtility.getCurrentUser();

        user.getWatchlist().add(movie.getMovieId());
        userRepository.save(user);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);

        return commonUtility.buildResponse(map, HttpStatus.OK);
    }

    public ResponseEntity<Object> likeReview(int reviewId){
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
        User user = commonUtility.getCurrentUser();

        if(!user.getLikedReviews().remove(review.getId())){
            review.setLikeCount(review.getLikeCount() + 1);
            reviewRepository.save(review);
            user.getLikedReviews().add(review.getId());
        }
        else {
            review.setLikeCount(Math.max(0, review.getLikeCount() - 1));
            reviewRepository.save(review);
        }

        userRepository.save(user);

        ReviewResponse reviewResponse = ReviewResponse.builder()
                .rating(review.getRating())
                .liked(user.getLikedReviews().contains(review.getId()))
                .title(review.getTitle())
                .id(review.getId())
                .likeCount(review.getLikeCount())
                .content(review.getContent())
                .user(userRepository.findById(review.getUserId()).orElse(null))
                .date(review.getDate())
                .build();

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("review", reviewResponse);

        return commonUtility.buildResponse(map, HttpStatus.OK);
    }

    public ResponseEntity<Object> makeReview(int movieId, ReviewDAO reviewDAO){
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
        User user = commonUtility.getCurrentUser();

        Review review = Review.builder()
                .userId(user.getId())
                .content(reviewDAO.getContent())
                .likeCount(0)
                .title(reviewDAO.getTitle())
                .rating(reviewDAO.getRating())
                .date(new Date())
                .build();

        movie.getReviews().add(review);
        movieRepository.save(movie);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);

        return commonUtility.buildResponse(map, HttpStatus.OK);
    }

    public ResponseEntity<Object> removeFromWatchlist(int movieId){
        User user = commonUtility.getCurrentUser();
        Integer toRemove = null;
        for(Integer movie: user.getWatchlist()){
            if(movie == movieId){
                toRemove = movie;
            }
        }
        if(toRemove != null){
            user.getWatchlist().remove(toRemove);
            userRepository.save(user);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return commonUtility.buildResponse(map, HttpStatus.OK);
    }

    public MovieListDTO getTrendingMovies(int page){
        return movieListProvider.getTrendingMovies(page);
    }

    public MovieListDTO getTopRatedMovies(int page) {
        return movieListProvider.getTopRatedMovies(page);
    }

    public MovieListDTO getUpComingMovies(int page) {
        return movieListProvider.getUpComingMovies(page);
    }

    public MovieListDTO getNowPlayingMovies(int page) {
        return movieListProvider.getNowPlayingMovies(page);
    }

    public MovieListDTO searchMovies(String query) {
        return movieListProvider.searchMovies(query);
    }
}
