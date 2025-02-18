package com.antekk.tetris.view.displays.score;

import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;

import static com.antekk.tetris.game.Shapes.getCurrentPlayer;

public class ScoreDisplay extends TetrisTextDisplay {

    public ScoreDisplay() {
        super(TetrisGamePanel.getBlockSizePx(), TetrisGamePanel.BOTTOM - 6 * TetrisGamePanel.getBlockSizePx(), "SCORE");
    }

    @Override
    protected void drawText(String text, Graphics g) {
        super.drawText(String.valueOf(getCurrentPlayer().score), g);
    }

    @Override
    protected int getHeightInBlocks() {
        return 3;
    }
}
