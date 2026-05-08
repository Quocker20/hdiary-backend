package com.hdiary.backend.dto.response;

import com.hdiary.backend.entity.Post;
import com.hdiary.backend.entity.enums.EventCategory;
import com.hdiary.backend.entity.enums.Mood;
import com.hdiary.backend.entity.enums.PostType;

import java.time.LocalDateTime;

public record PostResponse(
    Long id,
    UserResponse user,
    PostType postType,
    EventCategory eventCategory,
    Mood mood,
    String moodEmoji,
    String content,
    String imageUrl,
    LocalDateTime createdAt
) {
    public static PostResponse fromEntity(Post post) {
        return new PostResponse(
            post.getId(),
            UserResponse.fromEntity(post.getUser()),
            post.getPostType(),
            post.getEventCategory(),
            post.getMood(),
            post.getMood().getEmoji(),
            post.getContent(),
            post.getImageUrl(),
            post.getCreatedAt()
        );
    }
}
