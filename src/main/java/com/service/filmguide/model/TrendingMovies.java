package com.service.filmguide.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trending_movies")
public class TrendingMovies implements Serializable {

    public static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Movie movie;

    @Column(name = "page_number")
    private Integer pageNumber;

    

    /*public TrendDTO mapToTrendDTO(){
        return TrendDTO.builder()
                .movieId(this.movieId)
                .genreIds(this.genreIds)
                .lang(this.lang)
                .overview(this.overview)
                .posterUrl(this.posterUrl)
                .rate(this.rate)
                .releaseDate(this.releaseDate)
                .title(this.title)
                .popularity(this.popularity)
                .build();
    }*/
}
