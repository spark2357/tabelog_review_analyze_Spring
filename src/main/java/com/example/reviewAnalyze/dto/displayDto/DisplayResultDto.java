package com.example.reviewAnalyze.dto.displayDto;

import com.example.reviewAnalyze.dto.PlaceDto;
import com.example.reviewAnalyze.dto.ReviewDto;

import java.util.List;


public record DisplayResultDto(
        PlaceDto place,
        List<ReviewDto> reviews,
        List<DisplayKeywordDto> labeledKeywords
) {
}