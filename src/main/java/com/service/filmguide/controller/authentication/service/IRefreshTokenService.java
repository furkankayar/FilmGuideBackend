package com.service.filmguide.controller.authentication.service;

import com.service.filmguide.model.RefreshToken;

public interface IRefreshTokenService {

    RefreshToken generateRefreshToken();
    boolean validateRefreshToken(String token);

}
