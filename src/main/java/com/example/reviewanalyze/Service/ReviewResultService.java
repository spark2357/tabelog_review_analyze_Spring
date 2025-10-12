package com.example.reviewanalyze.Service;

import com.example.reviewanalyze.Domain.Review;
import com.example.reviewanalyze.Domain.ReviewResult;
import com.example.reviewanalyze.Domain.User;
import com.example.reviewanalyze.Dto.ReviewDto;
import com.example.reviewanalyze.Dto.ReviewResultDto;
import com.example.reviewanalyze.Repository.ReviewResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewResultService {
    private final ReviewResultRepository reviewResultRepository;

    @Transactional
    public ReviewResult saveReviewResultDto(User user, ReviewResultDto result){
        ReviewResult reviewResult = new ReviewResult();

        reviewResult.setName(result.getName());
        reviewResult.setAddress(result.getAddress());
        reviewResult.setUrl(result.getUrl());
        List<String> avgPrice = result.getPrice();
        reviewResult.setLunchPrice(avgPrice.getFirst());
        reviewResult.setDinnerPrice(avgPrice.getLast());
        reviewResult.setScore(result.getScore());
        reviewResult.setReviewCount(result.getReviewCount());
        reviewResult.setUser(user);

        List<Review> reviewList = new ArrayList<>();
        for (ReviewDto dto : result.getReviews()){
            Review review = new Review();

            review.setContent(dto.getContent());
            review.setScore(dto.getScore());
            review.setVisitYear(dto.getVisitYear());
            review.setVisitMonth(dto.getVisitMonth());
            review.setVisitTime(dto.getVisitTime());
            review.setVisitCount(dto.getVisitCount());
            review.setPurchased(dto.getPurchased());
            review.setEntitiesWithLabel(dto.getEntitiesWithLabel());
            review.setReviewResult(reviewResult);

            reviewList.add(review);
        }

        reviewResult.setReviews(reviewList);
        return reviewResultRepository.save(reviewResult);
    }

    /**
     * ID로 ReviewResult 조회
     */
    @Transactional(readOnly = true)
    public Optional<ReviewResult> findById(Long id) {
        return reviewResultRepository.findById(id);
    }

    /**
     * 전체 ReviewResult 조회
     */
    @Transactional(readOnly = true)
    public List<ReviewResult> findAll() {
        return reviewResultRepository.findAll();
    }

    /**
     * 삭제
     */
    @Transactional
    public void deleteById(Long id) {
        reviewResultRepository.deleteById(id);
    }
}
