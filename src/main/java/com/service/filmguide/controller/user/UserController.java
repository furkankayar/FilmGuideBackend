package com.service.filmguide.controller.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import com.service.filmguide.controller.authentication.utility.AuthenticationMapper;
import com.service.filmguide.controller.movie.response.MovieResponse;
import com.service.filmguide.controller.user.request.UserDAO;
import com.service.filmguide.controller.user.response.UserProfileDTO;
import com.service.filmguide.model.User;
import com.service.filmguide.controller.user.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AuthenticationMapper mapper;

    @GetMapping(value = "/{username}")
    public UserProfileDTO findProfileByUsername(@PathVariable String username) {
        return userService.findProfileByUsername(username);
    }


    @GetMapping
    public Collection<UserProfileDTO> findAll(){
        Iterable<User> users = this.userService.findAll();
        List<UserProfileDTO> userList = new ArrayList<UserProfileDTO>();
        users.forEach(user -> userList.add(mapper.userToUserDTO(user)));
        return userList;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public User newUser(@Valid @RequestBody UserDAO userDao){
        
        return this.userService.save(
            User.builder()
                .username(userDao.getUsername())
                .firstName(userDao.getFirstName())
                .lastName(userDao.getLastName())
                .build()
        );
    }

    @GetMapping("/{username}/watchlist")
    public ResponseEntity<Object> getWatchlist(@PathVariable String username){
        return userService.getWatchlist(username);
    }
}