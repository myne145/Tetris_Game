package com.antekk.tetris.view.displays.score;

import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;

import static com.antekk.tetris.game.Shapes.getLinesCleared;

public class LinesClearedDisplay extends TetrisTextDisplay {

    public LinesClearedDisplay() {
        super(TetrisGamePanel.getBlockSizePx(), TetrisGamePanel.TOP + 15 * TetrisGamePanel.getBlockSizePx(), "LINES CLEARED");
    }

    @Override
    protected void drawText(String text, Graphics g) {
        super.drawText(String.valueOf(getLinesCleared()), g);
    }

    @Override
    protected int getTitlePositionX() {
        return (int) (super.getTitlePositionX() - TetrisGamePanel.getBlockSizePx() * 1.3);
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
