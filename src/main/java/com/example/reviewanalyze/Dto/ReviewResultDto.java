package com.example.reviewanalyze.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReviewResultDto {
    private Info info;
    private List<ReviewDto> reviews;

    @Data
    private static class Info {
        @JsonProperty("display_name")
        private String name;
        private String url;
        private String address;
        @JsonProperty("price")
        private List<String> avgPrice;
        @JsonProperty("total_score")
        private Float score;
        @JsonProperty("review_cnt")
        private int reviewCount;
    }

    public String getName() {
        return info.getName();
    }
    public String getUrl() {
        return info.getUrl();
    }
    public String getAddress() {
        return info.getAddress();
    }
    public List<String> getPrice() {
        return info.getAvgPrice();
    }
    public Float getScore() {
        return info.getScore();
    }
    public Integer getReviewCount() {
        return info.getReviewCount();
    }
}