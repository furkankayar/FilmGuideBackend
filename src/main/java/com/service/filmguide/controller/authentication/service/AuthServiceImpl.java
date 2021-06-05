package com.service.filmguide.controller.authentication.service;

import java.time.Instant;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.service.filmguide.controller.common.exception.UserNotFoundException;
import com.service.filmguide.controller.authentication.utility.AuthenticationMapper;
import com.service.filmguide.controller.authentication.request.LoginDAO;
import com.service.filmguide.controller.authentication.request.RegisterDAO;
import com.service.filmguide.controller.authentication.response.AuthenticationResponse;
import com.service.filmguide.controller.authentication.response.RegisterationResponse;
import com.service.filmguide.controller.user.response.UserProfileDTO;
import com.service.filmguide.model.User;
import com.service.filmguide.controller.user.repository.IRoleRepository;
import com.service.filmguide.controller.authentication.security.JwtTokenProvider;

import com.service.filmguide.controller.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component("authService")
public class AuthServiceImpl implements IAuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RefreshTokenServiceImpl refreshTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private AuthenticationMapper mapper;

    @Value("${jwt.expiration.time}")
    private long jwtExpirationInMillis;

    @Value("${jwt.token.name}")
    private String jwtTokenName;

    @Value("${refresh.token.name}")
    private String refreshTokenName;

    @Override
    public ResponseEntity<AuthenticationResponse> login(LoginDAO loginDAO) {
        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDAO.getUsername(), loginDAO.getPassword()));

        HttpHeaders responseHeaders = new HttpHeaders();
        String newAccessToken = "";
        String newRefreshToken = "";
        

        newAccessToken = jwtTokenProvider.createToken(loginDAO.getUsername(), this.userService.findByUsername(loginDAO.getUsername()).orElseThrow(
                    () -> new UsernameNotFoundException("Username " + loginDAO.getUsername() + " not found"))
                    .getAuthorities());
        newRefreshToken = refreshTokenService.generateRefreshToken().getToken();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieGenerator(jwtTokenName, newAccessToken).toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieGenerator(refreshTokenName, newRefreshToken).toString());


        return ResponseEntity.ok()
                             .headers(responseHeaders)
                             .body(
                                AuthenticationResponse.builder()
                                    .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis))
                                    .username(loginDAO.getUsername())
                                    .jwtToken(newAccessToken)
                                    .refreshToken(newRefreshToken)
                                    .build()
                            );
    }

    @Override
    public ResponseEntity<AuthenticationResponse> refreshToken(String refreshToken, String accessToken) {
        
        refreshTokenService.validateRefreshToken(refreshToken);
       
        User user = userService.findByUsername(jwtTokenProvider.getUsername(accessToken))
            .orElseThrow(() -> new UserNotFoundException(jwtTokenProvider.getUsername(accessToken)));
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getAuthorities());
       
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieGenerator(jwtTokenName, token).toString());

        return ResponseEntity.ok()
                             .headers(responseHeaders)
                             .body(
                                AuthenticationResponse.builder()
                                    .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis))
                                    .username(user.getUsername())
                                    .jwtToken(token)
                                    .refreshToken(refreshToken)
                                    .build()
                            );
    }

    @Override
    public RegisterationResponse register(RegisterDAO registerDAO){

        registerDAO.setPassword(passwordEncoder.encode(registerDAO.getPassword()));
        User newUser = mapper.registerDAOToUser(registerDAO);
        newUser.getRoles().add(roleRepository.findByRole("user"));
        userService.save(newUser);
 
        return RegisterationResponse.builder().status(HttpStatus.OK).message("Registration successful!").build();
    }

    @Override
    public UserProfileDTO whoami(String accessToken){
        return userService.findProfileByUsername(jwtTokenProvider.getUsername(accessToken));
    }

    @Override
    public ResponseEntity<Object> logout(){

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieGenerator(jwtTokenName, "", 0).toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieGenerator(refreshTokenName, "", 0).toString());

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(responseHeaders)
                             .body(Stream.of(new AbstractMap.SimpleEntry<>("message", "Logged out successfully!"))
                             .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    private ResponseCookie cookieGenerator(String name, String token){
        return ResponseCookie.from(name, token)
                            .maxAge(Integer.MAX_VALUE)
                            .httpOnly(true)
                            .path("/")
                            .secure(false)
                            .build();
    } 

    private ResponseCookie cookieGenerator(String name, String token, int age){
        return ResponseCookie.from(name, token)
                            .maxAge(age)
                            .httpOnly(true)
                            .path("/")
                            .secure(false)
                            .build();
    } 
}