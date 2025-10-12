package com.example.reviewanalyze.Domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Table(name="review")
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

    private String purchased;

    @ElementCollection
    @CollectionTable(
            name = "review_entities_with_label",
            joinColumns = @JoinColumn(name = "review_id")
    )
    @MapKeyColumn(name = "entity")
    @Column(name = "label")
    private Map<String, String> entitiesWithLabel = new HashMap<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_result_id")
    private ReviewResult reviewResult;
}
