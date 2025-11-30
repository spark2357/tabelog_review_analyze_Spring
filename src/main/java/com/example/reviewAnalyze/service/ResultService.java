package com.example.reviewAnalyze.service;

import com.example.reviewAnalyze.dto.AnalyzedResultDto;
import com.example.reviewAnalyze.dto.KeywordDto;
import com.example.reviewAnalyze.dto.PlaceDto;
import com.example.reviewAnalyze.dto.ReviewDto;
import com.example.reviewAnalyze.dto.displayDto.DisplayResultDto;
import com.example.reviewAnalyze.dto.mapper.CustomKeywordMapper;
import com.example.reviewAnalyze.dto.mapper.PlaceMapper;
import com.example.reviewAnalyze.dto.mapper.ReviewMapper;
import com.example.reviewAnalyze.entity.*;
import com.example.reviewAnalyze.repository.KeywordRepository;
import com.example.reviewAnalyze.repository.PlaceRepository;
import com.example.reviewAnalyze.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultService {

    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final KeywordRepository keywordRepository;

    private final ReviewMapper reviewMapper;
    private final PlaceMapper placeMapper;
    private final CustomKeywordMapper keywordMapper;

    public DisplayResultDto getReviewResult(Long placeId){
        Place place = placeRepository.findById(placeId).orElseThrow(NoSuchElementException::new);
        List<Review> reviewList = reviewRepository.findAllByPlace(place);
        List<Keyword> keywordList = keywordRepository.findAllByPlace(place);

        return new DisplayResultDto(placeMapper.toDto(place), reviewMapper.toDtoList(reviewList), keywordMapper.toDtoList(keywordList));
    }

    public Page<Place> getResultListByUserId(Long userId, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return placeRepository.findByUserId(userId, pageable);
    }

    public Long saveResult(User user, AnalyzedResultDto result){
        Place place = savePlace(user, result.place());
        saveReviews(result.reviews(), place);
        saveKeywords(result.labeledKeywords(), place);

        return place.getId();
    }

    private Place savePlace(User user, PlaceDto result){
        Place place = Place.builder()
                .name(result.name())
                .address(result.address())
                .url(result.url())
                .lunchPrice(result.lunchPrice())
                .dinnerPrice(result.dinnerPrice())
                .score(result.score())
                .reviewCount(result.reviewCount())
                .user(user)
                .build();

        return placeRepository.save(place);
    }

    private void saveReviews(List<ReviewDto> reviews, Place place){

        for (ReviewDto reviewDto : reviews){

            String escapedContent = HtmlUtils.htmlEscape(reviewDto.content());

            String visitTime = reviewDto.visitTime();
            if(visitTime.equals("lunch")) visitTime = "점심";
            else if(visitTime.equals("dinner")) visitTime = "저녁";
            else visitTime = "-";

            Review review = Review.builder()
                    .content(escapedContent.replace("\n", "<br>"))
                    .score(reviewDto.score())
                    .visitYear(reviewDto.visitYear())
                    .visitMonth(reviewDto.visitMonth())
                    .visitTime(visitTime)
                    .visitCount(reviewDto.visitCount())
                    .usedPrice(reviewDto.usedPrice())
                    .place(place)
                    .build();

            reviewRepository.save(review);
        }
    }

    private void saveKeywords(KeywordDto keywordDto, Place place){
        // 이상한 label이 들어오면 IllegalArgumentException 이 올라옴.
        keywordDto.labeledKeywords().forEach((label, innerMap) -> {
            innerMap.forEach((word, count) -> saveKeyword(word, LabelType.findByFieldName(label).name(), count, place));
        });
    }

    private void saveKeyword(String word, String label, Integer count, Place place){
        Keyword keyword = Keyword.builder()
                .keyword(word)
                .label(label)
                .count(count)
                .place(place)
                .build();

        keywordRepository.save(keyword);
    }
}
