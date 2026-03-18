-- ==========================================
-- 1. User Service를 위한 데이터베이스 (user_db)
-- ==========================================
CREATE DATABASE IF NOT EXISTS user_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE user_db;

-- 기존 테이블이 있다면 삭제
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,  
    password VARCHAR(255) NOT NULL,        
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 초기 테스트 데이터 삽입 
-- (비밀번호는 모두 '1234'의 Bcrypt 해시값입니다)
INSERT INTO users (username, password, name, email) VALUES 
('gemini_dev', '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '김제미니', 'gemini@example.com'),
('apollo_graphql', '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '박아폴로', 'apollo@example.com');


-- ==========================================
-- 2. Content Service를 위한 데이터베이스 (content_db)
-- ==========================================
CREATE DATABASE IF NOT EXISTS content_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE content_db;

DROP TABLE IF EXISTS shows;

CREATE TABLE shows (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    release_year INT,
    preview_video_url VARCHAR(500),        
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 초기 테스트 데이터 삽입 (가상의 CDN URL 적용)
INSERT INTO shows (title, description, release_year, preview_video_url) VALUES 
('기묘한 이야기', '인디애나주의 작은 마을에서 벌어지는 미스터리한 사건들', 2016, 'https://cdn.example.com/trailers/stranger_things_720p.mp4'),
('오징어 게임', '456억 원의 상금이 걸린 의문의 서바이벌에 참가한 사람들', 2021, 'https://cdn.example.com/trailers/squid_game_720p.mp4');


-- ==========================================
-- 3. Review Service를 위한 데이터베이스 (review_db)
-- ==========================================
CREATE DATABASE IF NOT EXISTS review_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE review_db;

DROP TABLE IF EXISTS reviews;

CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL, 
    show_id BIGINT NOT NULL, 
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 초기 테스트 데이터 삽입 
INSERT INTO reviews (user_id, show_id, rating, comment) VALUES 
(1, 1, 5, '최고의 시리즈입니다!'),
(1, 2, 4, '긴장감이 대단하네요.'),
(2, 1, 3, '시즌 1이 제일 재밌었어요.');