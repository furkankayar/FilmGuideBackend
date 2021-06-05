package com.service.filmguide.controller.movie.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.service.filmguide.model.Person;
import com.service.filmguide.model.SpokenLanguage;
import com.service.filmguide.model.Video;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {

    @JsonProperty("movie_id")
    private Integer movieId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("tagline")
    private String tagline;

    @JsonProperty("vote_average")
    private float rate;

    @JsonProperty("original_language")
    private String lang;

    @JsonProperty("genres")
    @Builder.Default
    private List<GenreResponse> genres = new ArrayList<>();

    @JsonProperty("spoken_languages")
    @Builder.Default
    private List<SpokenLanguage> spokenLanguages = new ArrayList<>();

    @JsonProperty("cast")
    @Builder.Default
    private List<Person> cast = new ArrayList<>();

    @JsonProperty("videos")
    @Builder.Default
    private List<Video> videos = new ArrayList<>();

    @JsonProperty("reviews")
    private List<ReviewResponse> reviews = new ArrayList<>();

    @JsonProperty("watchlisted")
    private Boolean watchlisted;

}
