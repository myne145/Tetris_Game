package com.antekk.tetris.blocks;

import com.antekk.tetris.gameview.GamePanel;

import java.awt.*;

public interface HeldShape {

    void setHeld();

    default void drawHeldShape(Graphics g) {
        for (Point p : Shapes.getHeldShape().getCollisionPoints()) {
            //Fill
            g.setColor(Shapes.getHeldShape().getColor());
            g.fillRect(p.x, GamePanel.TOP + p.y, Shapes.getBlockSizePx(), Shapes.getBlockSizePx());

            //Border
            g.setColor(Color.BLACK);
            g.drawRect(p.x, GamePanel.TOP + p.y, Shapes.getBlockSizePx(), Shapes.getBlockSizePx());
        }
    }


}
