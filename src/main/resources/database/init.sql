-- hdiary_db.users definition

CREATE TABLE `users`
(
    `id`               bigint      NOT NULL AUTO_INCREMENT,
    `username`         varchar(50) NOT NULL,
    `display_name`     varchar(100) DEFAULT NULL,
    `avatar_url`       varchar(255) DEFAULT NULL,
    `avatar_public_id` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- hdiary_db.posts definition

CREATE TABLE `posts`
(
    `id`              bigint      NOT NULL AUTO_INCREMENT,
    `user_id`         bigint      NOT NULL,
    `post_type`       enum('NORMAL','EVENT') DEFAULT 'NORMAL',
    `event_category`  enum('SALARY','WEEKEND','REWARD','MOOD_UP','DATING') DEFAULT NULL,
    `mood`            varchar(20) NOT NULL,
    `content`         text,
    `image_url`       varchar(255) DEFAULT NULL,
    `image_public_id` varchar(100) DEFAULT NULL,
    `created_at`      timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY               `idx_user_created` (`user_id`,`created_at` DESC),
    KEY               `idx_created_at` (`created_at` DESC),
    KEY               `idx_mood` (`mood`),
    CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;