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
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;
    private String label;
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="place_id")
    private Place place;

}
