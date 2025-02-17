package com.antekk.tetris.game.player;

public enum ScoreValue {
    SIGLE(100),
    DOUBLE(300),
    TRIPLE(500),
    TETRIS(800),
    COMBO(50);


    private int value;
    ScoreValue(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }

    public static ScoreValue fromClearedLines(int linesCleared) {
        return ScoreValue.values()[linesCleared - 1];
    }

    public ScoreValue multiplyBy(int value) {
        this.value *= value;
        return this;
    }

//    public ScoreValue

}
