package com.service.filmguide.controller.movie.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.service.filmguide.model.Genre;
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

    @Override
    public boolean equals(Object genre){
        if(genre instanceof Genre){
            return this.id.equals(((Genre)genre).getId());
        }
        else if(genre instanceof GenreResponse){
            return this.id.equals(((GenreResponse)genre).getId());
        }
        return this.equals(genre);
    }
}
