package com.hdiary.backend.dto.response;

import com.hdiary.backend.entity.User;

public record UserResponse(
    Long id,
    String username,
    String displayName,
    String avatarUrl
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getDisplayName(),
            user.getAvatarUrl()
        );
    }
}
