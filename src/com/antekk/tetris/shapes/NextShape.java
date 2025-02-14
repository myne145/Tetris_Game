package com.antekk.tetris.shapes;

import com.antekk.tetris.gameview.GamePanel;

import java.awt.*;

public interface NextShape {
    void setAsNextShape();

    default void drawAsNextShape(Graphics g) {
        for (Point p : Shapes.getNextShape().getCollisionPoints()) {
            //Fill
            g.setColor(Shapes.getNextShape().getColor());
            g.fillRect(p.x, GamePanel.TOP + p.y, Shapes.getBlockSizePx(), Shapes.getBlockSizePx());

            //Border
            g.setColor(Color.BLACK);
            g.drawRect(p.x, GamePanel.TOP + p.y, Shapes.getBlockSizePx(), Shapes.getBlockSizePx());
        }
    }
}
