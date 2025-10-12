package com.example.reviewanalyze.Repository;

import com.example.reviewanalyze.Domain.ReviewResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewResultRepository extends JpaRepository<ReviewResult, Long> {

}