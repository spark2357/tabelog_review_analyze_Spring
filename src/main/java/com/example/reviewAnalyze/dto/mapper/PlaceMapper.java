package com.example.reviewAnalyze.dto.mapper;

import com.example.reviewAnalyze.dto.analyzeDto.PlaceDto;
import com.example.reviewAnalyze.entity.Place;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    PlaceDto toDto(Place place);
}
