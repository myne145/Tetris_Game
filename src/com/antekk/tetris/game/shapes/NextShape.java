package com.antekk.tetris.game.shapes;

import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;

import static com.antekk.tetris.game.Shapes.getBlockSizePx;
import static com.antekk.tetris.game.Shapes.getNextShape;

public interface NextShape {
    void setAsNextShape();

    default void drawAsNextShape(Graphics g) {
        for (Point p : getNextShape().getCollisionPoints()) {
            //Fill
            g.setColor(getNextShape().getDefaultColor());
            g.fillRect(p.x, TetrisGamePanel.TOP + p.y, getBlockSizePx(), getBlockSizePx());

            //Border
            g.setColor(Color.BLACK);
            g.drawRect(p.x, TetrisGamePanel.TOP + p.y, getBlockSizePx(), getBlockSizePx());
        }
    }
}
