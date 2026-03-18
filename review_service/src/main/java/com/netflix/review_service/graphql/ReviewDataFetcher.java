package com.netflix.review_service.graphql;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsEntityFetcher;
import com.netflix.review_service.model.Review;
import com.netflix.review_service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@DgsComponent
@RequiredArgsConstructor
public class ReviewDataFetcher {

    private final ReviewRepository reviewRepository;

    @DgsQuery
    public Review review(@InputArgument Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    // -------------------------------------------------------------------
    // [1] Review -> User & Show 연결 (리뷰 조회 시 껍데기 ID만 리턴)
    // -------------------------------------------------------------------
    @DgsData(parentType = "Review", field = "user")
    public Map<String, Object> fetchUserForReview(DgsDataFetchingEnvironment dfe) {
        Review review = dfe.getSource();
        // 실제 Entity 객체 대신 ID만 담은 Map을 리턴하면, 라우터가 알아서 User Service로 토스함!
        return Map.of("id", review.getUserId().toString());
    }

    @DgsData(parentType = "Review", field = "show")
    public Map<String, Object> fetchShowForReview(DgsDataFetchingEnvironment dfe) {
        Review review = dfe.getSource();
        return Map.of("id", review.getShowId().toString());
    }

    // -------------------------------------------------------------------
    // [2] 외부 타입(User, Show)의 확장(extends) 필드 구현
    // User/Show 서비스에서 "이 유저의 리뷰 목록 줘!", "이 영화의 리뷰 목록 줘!" 할 때 실행됨
    // -------------------------------------------------------------------
    @DgsEntityFetcher(name = "User")
    public Map<String, Object> resolveUser(Map<String, Object> values) {
        return values; // 라우터가 넘겨준 User ID를 그대로 유지
    }

    @DgsData(parentType = "User", field = "reviews")
    public List<Review> fetchReviewsForUser(DgsDataFetchingEnvironment dfe) {
        Map<String, Object> user = dfe.getSource();
        Long userId = Long.parseLong((String) user.get("id"));
        return reviewRepository.findByUserId(userId);
    }

    @DgsEntityFetcher(name = "Show")
    public Map<String, Object> resolveShow(Map<String, Object> values) {
        return values; // 라우터가 넘겨준 Show ID를 그대로 유지
    }

    @DgsData(parentType = "Show", field = "reviews")
    public List<Review> fetchReviewsForShow(DgsDataFetchingEnvironment dfe) {
        Map<String, Object> show = dfe.getSource();
        Long showId = Long.parseLong((String) show.get("id"));
        return reviewRepository.findByShowId(showId);
    }
}