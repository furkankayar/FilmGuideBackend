package com.service.filmguide.controller.authentication.service;

import com.service.filmguide.controller.authentication.request.LoginDAO;
import com.service.filmguide.controller.authentication.request.RegisterDAO;
import com.service.filmguide.controller.authentication.response.AuthenticationResponse;
import com.service.filmguide.controller.authentication.response.RegisterationResponse;
import com.service.filmguide.controller.user.response.UserProfileDTO;
import com.service.filmguide.controller.authentication.security.InvalidJwtAuthenticationException;

import org.springframework.http.ResponseEntity;

public interface IAuthService {
    
    ResponseEntity<AuthenticationResponse> login(LoginDAO loginDAO) throws InvalidJwtAuthenticationException;
    ResponseEntity<AuthenticationResponse> refreshToken(String refreshToken, String accessToken);
    RegisterationResponse register(RegisterDAO registerDAO);
    UserProfileDTO whoami(String accessToken);
    ResponseEntity<Object> logout();

}