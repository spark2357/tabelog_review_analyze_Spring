package com.example.reviewAnalyze.dto.mapper;

import com.example.reviewAnalyze.dto.displayDto.DisplayKeywordDto;
import com.example.reviewAnalyze.dto.displayDto.KeywordCount;
import com.example.reviewAnalyze.entity.Keyword;
import com.example.reviewAnalyze.entity.LabelType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomKeywordMapper {
    public List<DisplayKeywordDto> toDtoList(List<Keyword> keywordList){
        Map<String, List<Keyword>> groupedByLabel = keywordList.stream()
                .collect(Collectors.groupingBy(Keyword::getLabel));

        List<DisplayKeywordDto> sortedLabeledKeywords = new ArrayList<>();

        for(LabelType label: LabelType.values()){
            try{
                List<KeywordCount> keywordCountList = groupedByLabel.get(label.name())
                        .stream()
                        .map(k -> new KeywordCount(k.getKeyword(), k.getCount()))
                        .sorted(Comparator.comparingInt(KeywordCount::count).reversed())
                        .toList();

                sortedLabeledKeywords.add(new DisplayKeywordDto(label.getDisplayName(), keywordCountList));

            } catch(NullPointerException e){
                sortedLabeledKeywords.add(new DisplayKeywordDto(label.getDisplayName(), List.of()));
            }
        }
        return sortedLabeledKeywords.stream().toList();
    }
}
