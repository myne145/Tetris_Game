package com.antekk.tetris.game.player;

import com.antekk.tetris.view.displays.ScoreRewardDisplay;

public class TetrisPlayer {
    private ScoreRewardDisplay scoreDisplay;
    public static final PlayersStatsJSON playerStats = new PlayersStatsJSON();
    public long score = 0;
    public long linesCleared = 0;
    public int level = 0;
    public String name;

    public void addScore(ScoreValue scoreValue) {
        int val = scoreValue.getValue();
        if(scoreValue.isMultipliedByGameLevel())
            val *= level;

        score += val;

        if(scoreValue.toString() == null)
            return;

        scoreDisplay.setText(scoreValue.toString(), "+" + val + " points");
    }

    public void addScore(int value) {
        score += value;
    }

    /**
     * Current game player constructor
     * @param scoreDisplay where score rewards are gonna be displayed
     */
    public TetrisPlayer(ScoreRewardDisplay scoreDisplay) {
        this.scoreDisplay = scoreDisplay;
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
