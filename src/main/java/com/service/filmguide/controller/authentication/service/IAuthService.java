package com.service.filmguide.controller.authentication.service;

import com.service.filmguide.controller.authentication.request.LoginDAO;
import com.service.filmguide.controller.authentication.request.RegisterDAO;
import com.service.filmguide.controller.authentication.response.AuthenticationResponse;
import com.service.filmguide.controller.authentication.response.RegisterationResponse;
import com.service.filmguide.controller.user.response.UserProfileDTO;
import com.service.filmguide.security.InvalidJwtAuthenticationException;

import org.springframework.http.ResponseEntity;

public interface IAuthService {
    
    public ResponseEntity<AuthenticationResponse> login(LoginDAO loginDAO) throws InvalidJwtAuthenticationException;
    public ResponseEntity<AuthenticationResponse> refreshToken(String refreshToken, String accessToken);
    public RegisterationResponse register(RegisterDAO registerDAO);
    public UserProfileDTO whoami(String accessToken);
    public ResponseEntity<Object> logout();

}