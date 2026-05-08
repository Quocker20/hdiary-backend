package com.hdiary.backend.dto.response;

import com.hdiary.backend.entity.enums.Mood;

public interface MoodCountProjection {
    Mood getMood();
    Long getCount();
}
