package com.antekk.tetris.view.displays;

import com.antekk.tetris.view.TetrisColors;
import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;

import static com.antekk.tetris.view.TetrisGamePanel.getBlockSizePx;

public abstract class TetrisDisplay {
    protected final int x;
    protected final int y;
    protected final String text;

    protected final int textX;

    protected TetrisDisplay(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;

        textX = x + (int)(1.5 * TetrisGamePanel.getBlockSizePx());
    }

    public void drawDisplay(Graphics g) {
        g.setColor(TetrisColors.backgroundColor); //fill
        g.fillRoundRect(x + 1, y + 1, getWidthInBlocks() * getBlockSizePx() - 1, getHeightInBlocks() * getBlockSizePx() - 1,8,8);

        g.setColor(TetrisColors.foregroundColor); //border
        g.setFont(g.getFont().deriveFont(getTitleFontSize()).deriveFont(Font.BOLD));
        g.drawRoundRect(x, y, getWidthInBlocks() * getBlockSizePx(), getHeightInBlocks() * getBlockSizePx(),8,8);
        g.drawString(text, getTitlePositionX(), y + getBlockSizePx());
    }

    protected float getTitleFontSize() {
        return 36f;
    }

    protected int getTitlePositionX() {
        return x + (int)(1.5 * TetrisGamePanel.getBlockSizePx());
    }

    protected int getWidthInBlocks() {
        return 6;
    }

    protected int getHeightInBlocks() {
        return 6;
    }
}
