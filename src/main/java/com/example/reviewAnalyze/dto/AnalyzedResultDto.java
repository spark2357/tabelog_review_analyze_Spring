package com.example.reviewAnalyze.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record AnalyzedResultDto(
        PlaceDto place,
        List<ReviewDto> reviews,
        @JsonProperty("labeled_keywords") KeywordDto labeledKeywords
) {
}