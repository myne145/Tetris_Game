package com.antekk.tetris.gameview.displays.score;

import com.antekk.tetris.gameview.TetrisGamePanel;
import com.antekk.tetris.shapes.Shapes;

import java.awt.*;

public class ScoreDisplay extends TetrisTextDisplay {

    public ScoreDisplay() {
        super(Shapes.getBlockSizePx(), TetrisGamePanel.TOP + 11 * Shapes.getBlockSizePx(), "SCORE");
    }

    @Override
    protected void drawText(String text, Graphics g) {
        super.drawText(String.valueOf(Shapes.score), g);
    }

    @Override
    protected int getHeightInBlocks() {
        return 3;
    }
}
