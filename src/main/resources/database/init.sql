-- Table: users
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     username VARCHAR(50) NOT NULL,
    display_name VARCHAR(100),
    avatar_url VARCHAR(255),
    UNIQUE INDEX idx_username (username)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: posts
CREATE TABLE IF NOT EXISTS posts (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     user_id BIGINT NOT NULL,
                                     post_type ENUM('NORMAL', 'EVENT') DEFAULT 'NORMAL',
    event_category ENUM('SALARY', 'WEEKEND', 'REWARD', 'MOOD_UP', 'DATING'),
    mood VARCHAR(20) NOT NULL,
    content TEXT,
    image_url VARCHAR(255),
    image_public_id VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_created (user_id, created_at DESC),
    INDEX idx_created_at (created_at DESC),
    INDEX idx_mood (mood),

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;