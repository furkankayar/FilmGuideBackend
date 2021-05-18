package com.service.filmguide.controller.movie.repository;

import com.service.filmguide.controller.movie.model.TrendingMovies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITrendingMoviesRepository extends JpaRepository<TrendingMovies, Integer> {

    @Query("SELECT t FROM TrendingMovies t WHERE t.pageNumber = :pageNumber")
    List<TrendingMovies> findByPageNumber(@Param("pageNumber") int pageNumber);
}
