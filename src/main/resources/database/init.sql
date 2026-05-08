-- hdiary_db.users definition

CREATE TABLE users
(
    id               BIGSERIAL PRIMARY KEY,
    username         VARCHAR(50) NOT NULL UNIQUE,
    display_name     VARCHAR(100) DEFAULT NULL,
    avatar_url       VARCHAR(255) DEFAULT NULL,
    avatar_public_id VARCHAR(100) DEFAULT NULL
);

-- hdiary_db.posts definition

CREATE TABLE posts
(
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT NOT NULL,
    post_type       VARCHAR(20) DEFAULT 'NORMAL',
    event_category  VARCHAR(20) DEFAULT NULL,
    mood            VARCHAR(20) NOT NULL,
    content         TEXT,
    image_url       VARCHAR(255) DEFAULT NULL,
    image_public_id VARCHAR(100) DEFAULT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX idx_user_created ON posts (user_id, created_at DESC);
CREATE INDEX idx_created_at ON posts (created_at DESC);
CREATE INDEX idx_mood ON posts (mood);