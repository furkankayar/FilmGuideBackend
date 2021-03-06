package com.service.filmguide.controller.movie.repository;

import com.service.filmguide.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovieRepository extends JpaRepository<Movie, Integer> {

}
