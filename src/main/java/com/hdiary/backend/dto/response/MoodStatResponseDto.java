package com.hdiary.backend.dto.response;

public record MoodStatResponseDto(
        String mood,
        String emoji,
        long count
) {
}
