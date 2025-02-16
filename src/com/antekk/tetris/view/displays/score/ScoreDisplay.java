package com.antekk.tetris.view.displays.score;

import com.antekk.tetris.view.TetrisGamePanel;
import com.antekk.tetris.game.Shapes;

import java.awt.*;

public class ScoreDisplay extends TetrisTextDisplay {

    public ScoreDisplay() {
        super(Shapes.getBlockSizePx(), TetrisGamePanel.TOP + 11 * Shapes.getBlockSizePx(), "SCORE");
    }

    @Override
    protected void drawText(String text, Graphics g) {
        super.drawText(String.valueOf(Shapes.getCurrentPlayer().score), g);
    }

    @Override
    protected int getHeightInBlocks() {
        return 3;
    }
}
