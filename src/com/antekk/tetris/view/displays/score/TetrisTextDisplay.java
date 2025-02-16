package com.antekk.tetris.view.displays.score;

import com.antekk.tetris.view.TetrisGamePanel;
import com.antekk.tetris.view.displays.TetrisDisplay;

import java.awt.*;

abstract class TetrisTextDisplay extends TetrisDisplay {

    protected void drawText(String text, Graphics g) {
        int textWidth = g.getFontMetrics().stringWidth(text);
        int center = (getWidthInBlocks() / 2 + 1) * TetrisGamePanel.getBlockSizePx() - textWidth / 2;
        g.drawString(text, center, (int) (y + 2.3 * TetrisGamePanel.getBlockSizePx()));
    }

    @Override
    public void drawDisplay(Graphics g) {
        super.drawDisplay(g);
        drawText("", g);
    }

    protected TetrisTextDisplay(int x, int y, String title) {
        super(x, y, title);
    }
}
