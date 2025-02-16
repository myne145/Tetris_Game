package com.antekk.tetris.gameview.displays.score;

import com.antekk.tetris.gameview.TetrisGamePanel;
import com.antekk.tetris.shapes.Shapes;

import java.awt.*;

public class LinesClearedDisplay extends TetrisTextDisplay {

    public LinesClearedDisplay() {
        super(Shapes.getBlockSizePx(), TetrisGamePanel.TOP + 15 * Shapes.getBlockSizePx(), "LINES CLEARED");
    }

    @Override
    protected void drawText(String text, Graphics g) {
        super.drawText(String.valueOf(Shapes.getLinesCleared()), g);
    }

    @Override
    protected int getTitlePositionX() {
        return (int) (super.getTitlePositionX() - Shapes.getBlockSizePx() * 1.3);
    }

    @Override
    protected int getHeightInBlocks() {
        return 3;
    }

    @Override
    protected float getTitleFontSize() {
        return 28f;
    }
}
