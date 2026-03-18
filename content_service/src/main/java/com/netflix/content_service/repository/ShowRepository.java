package com.netflix.content_service.repository;

import com.netflix.content_service.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {
}