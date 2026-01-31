package com.example.reviewAnalyze.dto.displayDto;

import com.example.reviewAnalyze.dto.analyzeDto.PlaceDto;
import com.example.reviewAnalyze.dto.analyzeDto.ReviewDto;

import java.util.List;


public record DisplayResultDto(
        PlaceDto place,
        List<ReviewDto> reviews,
        List<DisplayKeywordDto> labeledKeywords
) {
}