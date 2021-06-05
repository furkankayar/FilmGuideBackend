package com.service.filmguide.controller.authentication.repository;

import java.util.Optional;

import com.service.filmguide.model.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
    
    public Optional<RefreshToken> findByToken(String token);
    public void deleteByToken(String token);
    
}