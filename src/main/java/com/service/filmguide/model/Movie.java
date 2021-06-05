package com.service.filmguide.model;

import com.service.filmguide.themoviedb.dto.TrendDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @Column(name="movie_id")
    private Integer movieId;

    @Column(name="title")
    private String title;

    @Column(name="release_date")
    private String releaseDate;

    @Column(name="overview", length=2048)
    private String overview;

    @Column(name="poster_path")
    private String posterUrl;

    @Column(name="backdrop_path")
    private String backdropUrl;

    @Column(name="tagline")
    private String tagline;

    @Column(name="vote_average")
    private float rate;

    @Column(name="original_language")
    private String lang;

    @Column(name="genres")
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Set<Genre> genres = new HashSet<>();

    @Column(name="spoken_languages")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<SpokenLanguage> spokenLanguages = new HashSet<>();

    @Column(name="cast")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Person> cast = new HashSet<>();

    @Column(name = "videos")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Video> videos = new HashSet<>();

    @Column(name = "reviews")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Review> reviews = new HashSet<>();

    @Column(name = "fullyFetched")
    private boolean fullyFetched;


    public TrendDTO mapToTrendDTO(){
        List<Integer> genreIds = new ArrayList<>();
        for(Genre genre: this.genres){
            genreIds.add(genre.getId());
        }

        return TrendDTO.builder()
                .movieId(this.movieId)
                .genreIds(genreIds)
                .posterUrl(this.posterUrl)
                .rate(this.rate)
                .title(this.title)
                .releaseDate(this.releaseDate)
                .lang(this.lang)
                .overview(this.overview)
                .build();
    }

    @Override
    public boolean equals(Object movie){
        if(movie instanceof Movie)
            return ((Movie) movie).getMovieId().equals(this.movieId);
        return this.equals(movie);
    }
}
