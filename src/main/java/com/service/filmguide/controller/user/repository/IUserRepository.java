package com.service.filmguide.controller.user.repository;

import java.util.Optional;

import com.service.filmguide.model.User;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface IUserRepository extends PagingAndSortingRepository<User, Long>{
    
    public Optional<User> findByUsername(@Param("username") String username);
    
}