package com.example.reviewAnalyze.dto.displayDto;


import java.util.List;

public record DisplayKeywordDto(
        String labelName,
        List<KeywordCount> labeledKeywords
) {
}
