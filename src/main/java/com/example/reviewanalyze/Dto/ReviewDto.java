package com.example.reviewanalyze.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;

@Data
public class ReviewDto {
    @JsonProperty("review_id")
    private Integer id;
    private String content;
    private Float score;
    @JsonProperty("year")
    private String visitYear;
    @JsonProperty("month")
    private String visitMonth;
    @JsonProperty("time")
    private String visitTime;
    @JsonProperty("visit_cnt")
    private Integer visitCount;
    @JsonProperty("used_price")
    private String purchased;
    @JsonProperty("entities_with_label")
    private HashMap<String, String> entitiesWithLabel;
}
