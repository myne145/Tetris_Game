package com.antekk.tetris.shapes;

import com.antekk.tetris.gameview.TetrisGamePanel;

import java.awt.*;

public interface HeldShape {

    void setAsHeldShape();

    default void drawAsHeldShape(Graphics g) {
        for (Point p : Shapes.getHeldShape().getCollisionPoints()) {
            //Fill
            g.setColor(Shapes.getHeldShape().getColor());
            g.fillRect(p.x, TetrisGamePanel.TOP + p.y, Shapes.getBlockSizePx(), Shapes.getBlockSizePx());

            //Border
            g.setColor(Color.BLACK);
            g.drawRect(p.x, TetrisGamePanel.TOP + p.y, Shapes.getBlockSizePx(), Shapes.getBlockSizePx());
        }
    }


}
