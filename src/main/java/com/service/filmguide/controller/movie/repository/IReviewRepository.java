package com.service.filmguide.controller.movie.repository;

import com.service.filmguide.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReviewRepository  extends JpaRepository<Review, Integer> {
}
