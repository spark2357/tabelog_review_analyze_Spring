package com.example.reviewAnalyze.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum LabelType {
    MENU("메뉴", "MENU"),
    EXP("경험", "EXP"),
    AMB("분위기", "AMB"),
    PP("인원", "PP");

    private final String displayName;
    private final String fieldName;

    LabelType(String displayName, String fieldName){
        this.displayName = displayName;
        this.fieldName = fieldName;
    }

    public static LabelType findByFieldName(String fieldName){
        return Arrays.stream(values())
                .filter(label -> label.getFieldName().equals(fieldName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
