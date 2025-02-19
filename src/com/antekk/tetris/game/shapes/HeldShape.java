package com.antekk.tetris.game.shapes;

import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;

import static com.antekk.tetris.game.Shapes.getBlockSizePx;
import static com.antekk.tetris.game.Shapes.getHeldShape;

public interface HeldShape {

    void setAsHeldShape();

    default void drawAsHeldShape(Graphics g) {
        for (Point p : getHeldShape().getCollisionPoints()) {
            //Fill
            g.setColor(getHeldShape().getDefaultColor());
            g.fillRect(p.x, TetrisGamePanel.TOP + p.y, getBlockSizePx(), getBlockSizePx());

            //Border
            g.setColor(Color.BLACK);
            g.drawRect(p.x, TetrisGamePanel.TOP + p.y, getBlockSizePx(), getBlockSizePx());
        }
    }


}
