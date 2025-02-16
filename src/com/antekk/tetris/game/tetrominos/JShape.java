package com.antekk.tetris.game.tetrominos;

import com.antekk.tetris.game.shapes.Shape;
import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class JShape extends Shape {
    @Override
    public ArrayList<Point> getDefaultCollisionPoints() {
        return new ArrayList<>(Arrays.asList(
                new Point(4,0),
                new Point(3,-1),
                new Point(3,0),
                new Point(5, 0)
        ));
    }

    @Override
    public Color getDefaultColor() {
        return Color.BLUE;
    }


    @Override
    public void setAsHeldShape() {
        super.setAsHeldShape();
        move(-TetrisGamePanel.getBlockSizePx() / 2, TetrisGamePanel.getBlockSizePx() * 2);
    }

    @Override
    public void setAsNextShape() {
        super.setAsNextShape();
        move((int) (17.5 * TetrisGamePanel.getBlockSizePx()), 2 * TetrisGamePanel.getBlockSizePx());
    }
}
