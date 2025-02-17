package com.antekk.tetris.game.player;

public class TetrisPlayer {
    public long score = 0;
    public long linesCleared = 0;
    public int level = 1;


    public void addNonMultipliedScore(ScoreValue scoreValues) {
        score += (long) scoreValues.getValue() * level;
    }

    public void addNonMultipliedScore(int value) {
        score += (long) value * level;
    }
}
