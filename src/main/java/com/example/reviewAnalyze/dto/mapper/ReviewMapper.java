package com.example.reviewAnalyze.dto.mapper;

import com.example.reviewAnalyze.dto.ReviewDto;
import com.example.reviewAnalyze.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDto toDto(Review review);
    List<ReviewDto> toDtoList(List<Review> reviewList);
}
