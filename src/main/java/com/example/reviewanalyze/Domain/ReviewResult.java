package com.example.reviewanalyze.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="review_result")
public class ReviewResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String url;
    private String lunchPrice;
    private String dinnerPrice;
    private Float score;
    private Integer reviewCount;

    @OneToMany(mappedBy = "reviewResult", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
