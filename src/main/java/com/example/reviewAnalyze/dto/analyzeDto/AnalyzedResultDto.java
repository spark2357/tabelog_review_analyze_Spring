package com.example.reviewAnalyze.dto.analyzeDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record AnalyzedResultDto(
        @JsonProperty("user_id")
        Long userId,
        PlaceDto place,
        List<ReviewDto> reviews,
        @JsonProperty("labeled_keywords") KeywordDto labeledKeywords
) {
}