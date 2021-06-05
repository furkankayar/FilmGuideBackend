package com.service.filmguide.controller.common.utility;

import com.service.filmguide.controller.common.error.ApiError;
import com.service.filmguide.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class CommonUtility {

    @Bean
    public WebClient getWebClientBuilder(){
        return WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024))
                .build())
                .build();
    }

    public User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User){
            return (User)principal;
        } else {
            return null;
        }
    }

    public ResponseEntity<Object> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    public ResponseEntity<Object> buildResponse(Map<String, Object> map, HttpStatus status){
        return new ResponseEntity<>(map, status);
    }
}
