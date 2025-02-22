package com.antekk.tetris.game.player;

public enum ScoreValue {
    SIGLE(100, true, "Single"),
    DOUBLE(300, true, "Double"),
    TRIPLE(500, true, "Triple"),
    TETRIS(800, true, "Tetris"),
    COMBO(50, true, "Combo"),
    MOVE_DOWN(1, false, null),
    HARD_DROP(2, false, null);


    private final int value;
    private final boolean isMultipliedByGameLevel;
    private final String textOnRewardDisplay;
    ScoreValue(int value, boolean isMultipliedByGameLevel, String textOnRewardDisplay) {
        this.value = value;
        this.isMultipliedByGameLevel = isMultipliedByGameLevel;
        this.textOnRewardDisplay = textOnRewardDisplay;
    }

    public int getValue() {
        return value;
    }

    public boolean isMultipliedByGameLevel() {
        return isMultipliedByGameLevel;
    }

    public static ScoreValue fromClearedLines(int linesCleared) {
        return ScoreValue.values()[linesCleared - 1];
    }

    @Override
    public String toString() {
        return textOnRewardDisplay;
    }
}
