package com.hdiary.backend.entity.enums;

import lombok.Getter;

@Getter
public enum Mood {
    HAPPY("😊"),
    EXCITED("🤩"),
    SAD("😢"),
    TIRED("😴"),
    ANGRY("😡");

    private final String emoji;

    Mood(String emoji) {
        this.emoji = emoji;
    }
}
