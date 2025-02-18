package com.antekk.tetris.view.displays.score;

import com.antekk.tetris.game.Shapes;
import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;

public class LevelDisplay extends TetrisTextDisplay{

    @Override
    protected void drawText(String text, Graphics g) {
        super.drawText(String.valueOf(Shapes.getCurrentPlayer().level), g);
    }

    public LevelDisplay() {
        super(Shapes.getBlockSizePx(), TetrisGamePanel.BOTTOM - 10 * TetrisGamePanel.getBlockSizePx(), "LEVEL");
    }

    @Override
    protected int getHeightInBlocks() {
        return 3;
    }
}
