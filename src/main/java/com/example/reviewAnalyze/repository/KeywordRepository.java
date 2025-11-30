package com.example.reviewAnalyze.repository;

import com.example.reviewAnalyze.entity.Keyword;
import com.example.reviewAnalyze.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> findAllByPlace(Place place);
}
