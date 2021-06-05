package com.service.filmguide.controller.authentication.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginDAO {
    
    @NotBlank(message = "Missing username!")
    private String username;
    @NotBlank(message = "Missing password!")
    private String password;
}