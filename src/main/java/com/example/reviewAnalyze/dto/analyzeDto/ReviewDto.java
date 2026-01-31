package com.example.reviewAnalyze.dto.analyzeDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReviewDto(
        String content,
        Float score,
        @JsonProperty("visit_year")
        String visitYear,
        @JsonProperty("visit_month")
        String visitMonth,
        @JsonProperty("visit_time")
        String visitTime,
        @JsonProperty("visit_count")
        Integer visitCount,
        @JsonProperty("user_price")
        String usedPrice
) {

}
