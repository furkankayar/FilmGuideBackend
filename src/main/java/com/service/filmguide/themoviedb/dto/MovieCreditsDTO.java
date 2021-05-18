package com.service.filmguide.themoviedb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MovieCreditsDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("cast")
    private List<PersonDTO> cast;
}
