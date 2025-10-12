package com.example.reviewanalyze.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EntityInfo{
    private Long reviewId;
    private String entity;
    private String label;
}