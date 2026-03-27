package com.netflix.review_service.graphql;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsEntityFetcher;
import com.netflix.review_service.dto.Show;
import com.netflix.review_service.dto.User;
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
        return Map.of("id", review.getUserId().toString());
    }

    @DgsData(parentType = "Review", field = "show")
    public Map<String, Object> fetchShowForReview(DgsDataFetchingEnvironment dfe) {
        Review review = dfe.getSource();
        return Map.of("id", review.getShowId().toString());
    }

    // -------------------------------------------------------------------
    // [2] 외부 타입(User, Show)의 확장(extends) 필드 구현
    // -------------------------------------------------------------------
    @DgsEntityFetcher(name = "User")
    public User resolveUser(Map<String, Object> values) {
        Long id = Long.parseLong(String.valueOf(values.get("id")));
        return new User(id);
    }

    @DgsData(parentType = "User", field = "reviews")
    public List<Review> fetchReviewsForUser(DgsDataFetchingEnvironment dfe) {
        User user = dfe.getSource();
        Long userId = user.getId();
        return reviewRepository.findByUserId(userId);
    }

    @DgsEntityFetcher(name = "Show")
    public Show resolveShow(Map<String, Object> values) {
        Long id = Long.parseLong(String.valueOf(values.get("id")));
        return new Show(id);
    }

    @DgsData(parentType = "Show", field = "reviews")
    public List<Review> fetchReviewsForShow(DgsDataFetchingEnvironment dfe) {
        Show show = dfe.getSource();
        Long showId = show.getId();
        return reviewRepository.findByShowId(showId);
    }
}