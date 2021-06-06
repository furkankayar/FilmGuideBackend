package com.service.filmguide.controller.user.service;

import java.util.*;

import com.service.filmguide.controller.common.utility.CommonUtility;
import com.service.filmguide.controller.movie.repository.IMovieRepository;
import com.service.filmguide.controller.user.utility.UserMapper;
import com.service.filmguide.controller.user.response.UserProfileDTO;
import com.service.filmguide.model.Movie;
import com.service.filmguide.model.User;
import com.service.filmguide.controller.user.repository.IUserRepository;

import com.service.filmguide.themoviedb.dto.MovieListItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private CommonUtility commonUtility;

    @Autowired
    private IMovieRepository movieRepository;

    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }


    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Iterable<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public UserProfileDTO findProfileByUsername(String username){
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User is not found!"));
        return this.userMapper.userToUserProfileDTO(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        return byUsername.orElseThrow(() -> new UsernameNotFoundException("User is not found!"));
    }

    public ResponseEntity<Object> getWatchlist(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User is not found!"));
        User requestingUser = commonUtility.getCurrentUser();

        List<MovieListItemDTO> movies = new ArrayList<>();

        for(Integer movieId : user.getWatchlist()){
            Movie movie = movieRepository.findById(movieId).orElse(null);
            if(movie != null){
                MovieListItemDTO movieListItemDTO = movie.mapToTrendDTO();
                movieListItemDTO.setWatchlisted(requestingUser.getWatchlist().contains(movie.getMovieId()));
                movies.add(movieListItemDTO);
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("watchlist", movies);

        return commonUtility.buildResponse(map, HttpStatus.OK);
    }
}