package com.antekk.tetris.game.player;

public enum ScoreValue {
    SIGLE(100),
    DOUBLE(300),
    TRIPLE(500),
    TETRIS(800);


    private final int value;
    ScoreValue(int value) {
        this.value = value;
    }

    protected int getValue() {
        return value;
    }

    public static ScoreValue fromClearedLines(int linesCleared) {
        return ScoreValue.values()[linesCleared - 1];
    }

}
