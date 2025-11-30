package com.example.reviewAnalyze.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Float score;
    private String visitYear;
    private String visitMonth;
    private String visitTime;
    private Integer visitCount;
    private String usedPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;
}
