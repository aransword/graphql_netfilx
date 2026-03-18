package com.netflix.review_service.repository;

import com.netflix.review_service.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Federation 시 특정 콘텐츠(Show)에 달린 리뷰들을 찾기 위한 메서드
    List<Review> findByShowId(Long showId);

    // 특정 유저(User)가 작성한 리뷰들을 찾기 위한 메서드
    List<Review> findByUserId(Long userId);
}