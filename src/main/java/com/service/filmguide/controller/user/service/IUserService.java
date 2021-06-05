package com.service.filmguide.controller.user.service;

import java.util.Optional;

import com.service.filmguide.model.User;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService{
    
    public Optional<User> findById(Long id);
    public User save(User user);
    public Iterable<User> findAll();
    public Optional<User> findByUsername(String username);
}