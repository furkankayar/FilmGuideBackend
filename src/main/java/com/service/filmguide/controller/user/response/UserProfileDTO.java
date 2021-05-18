package com.service.filmguide.controller.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDTO {
    
    private String username;

    private String firstName;

    private String lastName;

    private String email;
    
}