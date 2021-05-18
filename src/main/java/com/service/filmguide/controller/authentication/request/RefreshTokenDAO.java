package com.service.filmguide.controller.authentication.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefreshTokenDAO {

    private String accessToken;

    @NotBlank(message="Refresh token is mandotary.")
    private String refreshToken;
}
