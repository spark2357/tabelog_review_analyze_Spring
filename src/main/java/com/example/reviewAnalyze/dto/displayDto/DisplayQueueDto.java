package com.example.reviewAnalyze.dto.displayDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DisplayQueueDto(
        @JsonProperty("queue_size") Integer queueSize,
        @JsonProperty("max_queue_size") Integer maxQueueSize
) {
}

