package com.service.filmguide.controller.movie.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenreResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;
}
