package com.example.reviewAnalyze.repository;

import com.example.reviewAnalyze.entity.Place;
import com.example.reviewAnalyze.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByPlace(Place place);
}
