package com.example.reviewAnalyze.service;

import com.example.reviewAnalyze.dto.analyzeDto.PlaceDto;
import com.example.reviewAnalyze.dto.analyzeDto.ReviewDto;
import com.example.reviewAnalyze.dto.displayDto.DisplayResultDto;
import com.example.reviewAnalyze.dto.mapper.PlaceMapper;
import com.example.reviewAnalyze.dto.mapper.ReviewMapper;
import com.example.reviewAnalyze.entity.Place;
import com.example.reviewAnalyze.entity.Review;
import com.example.reviewAnalyze.entity.User;
import com.example.reviewAnalyze.repository.KeywordRepository;
import com.example.reviewAnalyze.repository.PlaceRepository;
import com.example.reviewAnalyze.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ResultServiceTest {

    @Mock
    private PlaceRepository placeRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private KeywordRepository keywordRepository;
    @Mock
    private ReviewMapper reviewMapper;
    @Mock
    private PlaceMapper placeMapper;

    @InjectMocks
    private ResultService resultService;

    @Test
    void saveResult() {
    }

    @Test
    void getReviewResult() {
        User testUser = getTestUser();
        Place testPlace = getTestPlace(testUser);
        List<Review> testReviewList = getTestReviewList(testPlace);
        PlaceDto testPlaceDto = getTestPlaceDto();
        List<ReviewDto> testReviewDtoList = getTestReviewDtoList();

        when(placeRepository.findById(testPlace.getId())).thenReturn(Optional.of(testPlace));
        when(reviewRepository.findAllByPlace(testPlace)).thenReturn(testReviewList);
        when(placeMapper.toDto(testPlace)).thenReturn(testPlaceDto);
        when(reviewMapper.toDtoList(testReviewList)).thenReturn(testReviewDtoList);

        log.info("placeId={}", testPlace.getId());
        DisplayResultDto resultDto = resultService.getReviewResult(testPlace.getDisplayId());

        assertThat(resultDto.place()).isEqualTo(testPlaceDto);
        assertThat(resultDto.reviews()).isEqualTo(testReviewDtoList);
    }

    private User getTestUser(){
        return User.builder()
                .id(1L)
                .username("aaa")
                .password("aaa")
                .build();
    }

    private Place getTestPlace(User user){
        return Place.builder()
                .id(1L)
                .name("testName")
                .address("testAddress")
                .url("testUrl")
                .lunchPrice("testLunchPrice")
                .dinnerPrice("testDinnerPrice")
                .score(5.0f)
                .reviewCount(100)
                .user(user)
                .build();
    }

    private PlaceDto getTestPlaceDto(){
        return new PlaceDto("testName", "testUrl", "testAddress", "testDinnerPrice", "testLunchPrice", 5.0f, 100);
    }

    private List<Review> getTestReviewList(Place place){
        Review review1 = Review.builder()
                .id(1L)
                .content("testContent")
                .score(5.0f)
                .visitYear("testVisitYear")
                .visitMonth("testVisitMonth")
                .visitTime("testVisitTime")
                .visitCount(1)
                .usedPrice("testUsedPrice")
                .place(place)
                .build();

        Review review2 = Review.builder()
                .id(2L)
                .content("testContent")
                .score(5.0f)
                .visitYear("testVisitYear")
                .visitMonth("testVisitMonth")
                .visitTime("testVisitTime")
                .visitCount(1)
                .usedPrice("testUsedPrice")
                .place(place)
                .build();

        List<Review> testReviewList = new ArrayList<>();
        testReviewList.add(review1);
        testReviewList.add(review2);
        return testReviewList;
    }

    private List<ReviewDto> getTestReviewDtoList(){
        List<ReviewDto> testReviewDtoList = new ArrayList<>();
        ReviewDto reviewDto1 = new ReviewDto("testContent", 5.0f, "testVisitYear", "testVisitMonth", "testVisitTime", 100, "testUsedPrice");
        ReviewDto reviewDto2 = new ReviewDto("testContent", 5.0f, "testVisitYear", "testVisitMonth", "testVisitTime", 100, "testUsedPrice");
        testReviewDtoList.add(reviewDto1);
        testReviewDtoList.add(reviewDto2);
        return testReviewDtoList;
    }
}