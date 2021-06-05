package com.service.filmguide.controller.movie.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewDAO {

    @NotBlank(message = "Missing title!")
    private String title;

    @NotBlank(message = "Missing content!")
    private String content;

    private int rating;

}


