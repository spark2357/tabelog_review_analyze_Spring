package com.example.reviewAnalyze.repository;

import com.example.reviewAnalyze.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Page<Place> findByUserId(Long userId, Pageable pageable);
    Place findByDisplayId(String displayId);
}