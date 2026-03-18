package com.netflix.user_service.repository;

import com.netflix.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 나중에 로그인을 위해 username으로 찾는 메서드
    Optional<User> findByUsername(String username);
}