package com.service.filmguide.controller.movie.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.filmguide.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {

    private Integer id;

    private String title;

    private String content;

    private Integer rating;

    private User user;

    private Integer likeCount;

    private boolean liked;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date date;
}
