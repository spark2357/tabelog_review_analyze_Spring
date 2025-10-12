package com.example.reviewanalyze.Service;

import com.example.reviewanalyze.Domain.Review;
import com.example.reviewanalyze.Domain.ReviewResult;
import com.example.reviewanalyze.Domain.User;
import com.example.reviewanalyze.Dto.KeywordDto;
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

    public Map<String, List<KeywordDto>> getGroupEntitiesByLabel(ReviewResult reviewResult) {
        Map<String, List<KeywordDto>> groupedEntities = new HashMap<>();
        Set<String> seenKeys = new HashSet<>();

        for (Review review : reviewResult.getReviews()) {
            Long reviewId = review.getId();
            Map<String, String> entities = review.getEntitiesWithLabel();
            if (entities == null) continue;

            for (Map.Entry<String, String> entry : entities.entrySet()) {
                String entity = entry.getKey();
                String label = entry.getValue();

                if (seenKeys.contains(entity)) continue;
                seenKeys.add(entity);

                groupedEntities.computeIfAbsent(label, k -> new ArrayList<>())
                        .add(new KeywordDto(reviewId, entity));
            }
        }

        return groupedEntities;
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
    public Page<ReviewResult> getReviewResultsByUserId(Long userId, int page, int size, String sortBy, String direction) {
        System.out.println("userId = " + userId + ", page = " + page + ", size = " + size + ", sortBy = " + sortBy + ", direction = " + direction);
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return reviewResultRepository.findByUserId(userId, pageable);
    }

    /**
     * 삭제
     */
    @Transactional
    public void deleteById(Long id) {
        reviewResultRepository.deleteById(id);
    }
}
