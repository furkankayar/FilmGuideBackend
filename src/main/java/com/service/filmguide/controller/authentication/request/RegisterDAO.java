package com.service.filmguide.controller.authentication.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDAO {

    @NotBlank(message = "Username is mandotary!")
    @Length(min = 6, message = "Username must be at least 6 characters!")
    @Length(max = 15, message = "Username must be shorter than 15 characters!")
    private String username;
    
    @NotBlank(message = "First name is mandotary!")
    @Length(max = 20, message = "First name must be shorter than 20 characters!")
    private String firstName;

    @NotBlank(message = "Last name is mandotary!")
    @Length(max = 20, message = "Last name must be shorter than 20 characters!")
    private String lastName;

    @NotBlank(message = "Email is mandotary!")
    @Email(message = "Email pattern is invalid!")
    private String email;

    @NotBlank(message = "Password is mandotary!")
    @Length(min = 8, message = "Password must be at least 8 characters!")
    @Length(max = 15, message = "Password must be shorter than 15 characters!")
    private String password;

}