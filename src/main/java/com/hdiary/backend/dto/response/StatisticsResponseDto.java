package com.hdiary.backend.dto.response;

import java.util.List;

public record StatisticsResponseDto(
        List<MoodStatResponseDto> daily,
        List<MoodStatResponseDto> weekly
) {
}
