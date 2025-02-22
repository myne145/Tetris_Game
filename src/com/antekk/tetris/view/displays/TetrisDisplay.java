package com.antekk.tetris.view.displays;

import com.antekk.tetris.view.themes.TetrisColors;

import java.awt.*;

import static com.antekk.tetris.game.Shapes.getBlockSizePx;


public abstract class TetrisDisplay {
    protected final int x;
    protected final int y;
    protected final String text;

    protected final int textX;

    protected TetrisDisplay(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;

        textX = x + (int)(1.5 * getBlockSizePx());
    }

    public void drawDisplay(Graphics g) {
        g.setColor(TetrisColors.boardColor); //fill
        g.fillRoundRect(x + 1, y + 1, getWidthInBlocks() * getBlockSizePx() - 1, getHeightInBlocks() * getBlockSizePx() - 1,8,8);

        g.setColor(TetrisColors.borderColor); //border
        g.setFont(g.getFont().deriveFont(getTitleFontSize()).deriveFont(Font.BOLD));
        g.drawRoundRect(x, y, getWidthInBlocks() * getBlockSizePx(), getHeightInBlocks() * getBlockSizePx(),8,8);
        g.setColor(TetrisColors.foregroundColor);
        g.drawString(text, getTitlePositionX(), y + getBlockSizePx());
    }

    protected float getTitleFontSize() {
        return (float) (0.9 * getBlockSizePx());
    }

    protected int getTitlePositionX() {
        return x + (int)(1.5 * getBlockSizePx());
    }

    protected int getWidthInBlocks() {
        return 6;
    }

    protected int getHeightInBlocks() {
        return 6;
    }
}
