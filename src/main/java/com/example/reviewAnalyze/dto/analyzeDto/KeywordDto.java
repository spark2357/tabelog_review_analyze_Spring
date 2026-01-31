package com.example.reviewAnalyze.dto.analyzeDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.ConcurrentHashMap;

public record KeywordDto(
        @JsonProperty("labeled_keywords")
        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> labeledKeywords
) {
}
