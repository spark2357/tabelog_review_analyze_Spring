package com.example.reviewanalyze.Service;

import com.example.reviewanalyze.Domain.Review;
import com.example.reviewanalyze.Domain.ReviewResult;
import com.example.reviewanalyze.Domain.User;
import com.example.reviewanalyze.Dto.EntityInfo;
import com.example.reviewanalyze.Dto.ReviewDto;
import com.example.reviewanalyze.Dto.ReviewResultDto;
import com.example.reviewanalyze.Repository.ReviewResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
        reviewResult.setLunchPrice(avgPrice.getLast());
        reviewResult.setDinnerPrice(avgPrice.getFirst());
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
    public Map<String, Map<String, Long>> getAggregatedEntities(ReviewResult reviewResult) {
        List<EntityInfo> entityList  = new ArrayList<>();
        List<Review> reviewList = reviewResult.getReviews();
        for(Review review : reviewList){
            Long reviewId = review.getId();
            Map<String, String> entities = review.getEntitiesWithLabel();
            if (entities == null) continue;
            for(Map.Entry<String, String> entry : entities.entrySet()){
                entityList.add(new EntityInfo(reviewId, entry.getKey(), entry.getValue()));
            }
        }

        return entityList.stream()
                // 1. label 기준 그룹핑
                .collect(Collectors.groupingBy(
                        EntityInfo::getLabel, // label
                        Collectors.toList()
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, // label
                        e -> e.getValue().stream()
                                .collect(Collectors.groupingBy(
                                        EntityInfo::getEntity,
                                        Collectors.counting()
                                ))
                                .entrySet().stream()
                                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (oldVal, newVal) -> oldVal,
                                        LinkedHashMap::new
                                ))
                ));
    }
    /**
     * 전체 ReviewResult 조회
     */
    @Transactional(readOnly = true)
    public Page<ReviewResult> getReviewResultsByUserId(Long userId, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return reviewResultRepository.findByUserId(userId, pageable);
    }
    @Transactional(readOnly = true)
    public List<Review> findByEntity(ReviewResult reviewResult, String entity){
        List<Review> reviews = reviewResult.getReviews();
        List<Review> reviewsWithEntity = new ArrayList<>();
        for (Review review : reviews){
            if(review.getEntitiesWithLabel().containsKey(entity)){
                reviewsWithEntity.add(review);
            }
        }
        return reviewsWithEntity;
    }
    /**
     * 삭제
     */
    @Transactional
    public void deleteById(Long id) {
        reviewResultRepository.deleteById(id);
    }
}
