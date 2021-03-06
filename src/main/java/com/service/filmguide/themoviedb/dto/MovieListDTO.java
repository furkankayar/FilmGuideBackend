package com.service.filmguide.themoviedb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MovieListDTO {
    @JsonProperty("page")
    private int page;
    @JsonProperty("results")
    @Builder.Default
    private List<MovieListItemDTO> results = new ArrayList<>();
}

