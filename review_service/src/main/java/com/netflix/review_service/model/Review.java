package com.netflix.review_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 외래키 객체가 아닌 단순 ID 저장
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "show_id")
    private Long showId;

    private Integer rating;
    private String comment;
}