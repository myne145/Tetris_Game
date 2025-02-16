package com.antekk.tetris.shapes;

import com.antekk.tetris.gameview.TetrisGamePanel;

import java.awt.*;

public interface NextShape {
    void setAsNextShape();

    default void drawAsNextShape(Graphics g) {
        for (Point p : Shapes.getNextShape().getCollisionPoints()) {
            //Fill
            g.setColor(Shapes.getNextShape().getColor());
            g.fillRect(p.x, TetrisGamePanel.TOP + p.y, Shapes.getBlockSizePx(), Shapes.getBlockSizePx());

            //Border
            g.setColor(Color.BLACK);
            g.drawRect(p.x, TetrisGamePanel.TOP + p.y, Shapes.getBlockSizePx(), Shapes.getBlockSizePx());
        }
    }
}
