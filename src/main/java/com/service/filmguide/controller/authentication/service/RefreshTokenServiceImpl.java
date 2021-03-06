package com.service.filmguide.controller.authentication.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import com.service.filmguide.model.RefreshToken;
import com.service.filmguide.controller.authentication.repository.IRefreshTokenRepository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@Component("refreshTokenService")
@AllArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements IRefreshTokenService{
    
    private final IRefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    public boolean validateRefreshToken(String token){
        refreshTokenRepository.findByToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
        
        return true;
    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }
}