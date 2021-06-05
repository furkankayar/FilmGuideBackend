package com.service.filmguide.controller.authentication;

import javax.validation.Valid;

import com.service.filmguide.controller.authentication.request.LoginDAO;
import com.service.filmguide.controller.authentication.request.RefreshTokenDAO;
import com.service.filmguide.controller.authentication.request.RegisterDAO;
import com.service.filmguide.controller.authentication.response.AuthenticationResponse;
import com.service.filmguide.controller.authentication.response.RegisterationResponse;
import com.service.filmguide.controller.authentication.service.RefreshTokenServiceImpl;
import com.service.filmguide.controller.user.response.UserProfileDTO;
import com.service.filmguide.controller.authentication.service.AuthServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private RefreshTokenServiceImpl refreshTokenService;

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginDAO body){
        return authService.login(body);
    }

    @PostMapping(path = "/refresh/token")
    public ResponseEntity<AuthenticationResponse> refreshTokens(@Valid @RequestBody RefreshTokenDAO refreshTokenDAO){
        return authService.refreshToken(refreshTokenDAO.getRefreshToken(), refreshTokenDAO.getAccessToken());
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<Object> logout(
        @CookieValue(name = "Refresh_Token", required = true) String refreshToken
    ){
        refreshTokenService.deleteRefreshToken(refreshToken);
        return authService.logout();
    }
 
    @PostMapping(path = "/register")
    public RegisterationResponse register(@Valid @RequestBody RegisterDAO body){
        
        return authService.register(body);
    }

    @GetMapping(value = "/whoami")
    public UserProfileDTO whoami(
        @CookieValue(name = "Authorization", required = false) String accessToken
    )
    {
        return authService.whoami(accessToken);
    }
}