package com.service.filmguide.themoviedb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.service.filmguide.controller.movie.model.Genre;
import com.service.filmguide.controller.movie.model.Movie;
import com.service.filmguide.controller.movie.model.SpokenLanguage;
import lombok.*;

import java.util.HashSet;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MovieDTO {
    @JsonProperty("id")
    private Integer movieId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("genres")
    private List<Genre> genres;
    @JsonProperty("spoken_languages")
    private List<SpokenLanguage> spokenLanguages;
    @JsonProperty("vote_average")
    private float rate;
    @JsonProperty("original_language")
    private String lang;


    public Movie mapToMovie(){

        return Movie.builder()
                .movieId(this.movieId)
                .title(this.title)
                .releaseDate(this.releaseDate)
                .overview(this.overview)
                .posterUrl(this.posterPath)
                .genres(new HashSet<>(this.genres))
                .spokenLanguages(new HashSet<>(this.spokenLanguages))
                .rate(this.rate)
                .lang(this.lang)
                .build();
    }
}
