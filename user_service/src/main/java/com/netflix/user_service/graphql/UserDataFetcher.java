package com.netflix.user_service.graphql;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.DgsEntityFetcher;
import com.netflix.user_service.model.User;
import com.netflix.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@DgsComponent
@RequiredArgsConstructor
public class UserDataFetcher {

    private final UserRepository userRepository;

    // 1. 단일 유저 조회 (Query의 user 필드와 매핑)
    @DgsQuery(field = "user")
    public User user(@InputArgument Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // 2. 전체 유저 조회 (Query의 users 필드와 매핑)
    @DgsQuery(field = "users")
    public List<User> users() {
        return userRepository.findAll();
    }

    // ⭐ 3. Federation의 핵심! (다른 서브그래프에서 이 유저를 참조할 때 호출됨)
    @DgsEntityFetcher(name = "User")
    public User resolveUserReference(Map<String, Object> values) {
        // Apollo Router가 "id가 1인 User 데이터 좀 줘!" 라고 할 때 이 메서드가 실행돼.
        String idStr = (String) values.get("id");
        if (idStr != null) {
            Long id = Long.parseLong(idStr);
            return userRepository.findById(id).orElse(null);
        }
        return null;
    }
}