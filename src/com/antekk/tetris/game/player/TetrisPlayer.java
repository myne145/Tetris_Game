package com.antekk.tetris.game.player;

public class TetrisPlayer {
    public static final PlayersStatsJSON playerStats = new PlayersStatsJSON();
    public long score = 0;
    public long linesCleared = 0;
    public int level = 0;
    public String name;

    public void addNonMultipliedScore(ScoreValue scoreValues) {
        score += (long) scoreValues.getValue() * level;
    }

    public void addNonMultipliedScore(int value) {
        score += (long) value * level;
    }

    public void addMultipliedScore(ScoreValue scoreValue) {
            score += scoreValue.getValue();
    }

    public TetrisPlayer() {

    }

    public TetrisPlayer(long score, long linesCleared, int level, String name) {
        this.score = score;
        this.linesCleared = linesCleared;
        this.level = level;
        this.name = name;
    }

    public static PlayersStatsJSON getStatsFile() {
        return playerStats;
    }
}
