package com.example.reviewAnalyze.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlaceDto(
        @JsonProperty("display_name")
        String name,
        String url,
        String address,
        @JsonProperty("lunch_price")
        String lunchPrice,
        @JsonProperty("dinner_price")
        String dinnerPrice,
        @JsonProperty("total_score")
        Float score,
        @JsonProperty("review_count")
        Integer reviewCount
) {
}
