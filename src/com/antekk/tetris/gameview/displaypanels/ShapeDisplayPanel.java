package com.antekk.tetris.gameview.displaypanels;

import com.antekk.tetris.gameview.TetrisColors;
import com.antekk.tetris.shapes.Shapes;

import java.awt.*;

import static com.antekk.tetris.shapes.Shapes.getBlockSizePx;

public abstract class ShapeDisplayPanel {
    private final int x;
    private final int y;
    private final String text;

    abstract void drawShape(Graphics g);

    public void drawPanel(Graphics g) {
        //hold
        g.setColor(TetrisColors.backgroundColor); //fill
        g.fillRoundRect(x + 1, y + 1, 6 * getBlockSizePx() - 1, 6 * getBlockSizePx() - 1,8,8);

        g.setColor(TetrisColors.foregroundColor); //border
        g.setFont(g.getFont().deriveFont(36f).deriveFont(Font.BOLD));
        g.drawRoundRect(x, y, 6 * getBlockSizePx(), 6 * getBlockSizePx(),8,8);
        g.drawString(text, x + (int)(1.5 * Shapes.getBlockSizePx()), y + getBlockSizePx());

        drawShape(g);
    }

    protected ShapeDisplayPanel(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }

}
