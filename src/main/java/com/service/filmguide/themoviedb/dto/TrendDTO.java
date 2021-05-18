package com.service.filmguide.themoviedb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TrendDTO {
    @JsonProperty("id")
    private int movieId;
    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
    @JsonProperty("poster_path")
    private String posterUrl;
    @JsonProperty("vote_average")
    private float rate;
    @JsonProperty("title")
    private String title;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("original_language")
    private String lang;
    @JsonProperty("overview")
    private String overview;

    @JsonProperty("watchlisted")
    private Boolean watchlisted;

    /*public TrendingMovies mapToTrendingMovies(int pageNumber){
        return TrendingMovies.builder()
                .movieId(this.movieId)
                .pageNumber(pageNumber)
                .genreIds(this.genreIds)
                .posterUrl(this.posterUrl)
                .rate(this.rate)
                .title(this.title)
                .releaseDate(this.releaseDate)
                .lang(this.lang)
                .overview(this.overview)
                .popularity(this.popularity)
                .build();
    }*/
}
