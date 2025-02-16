package com.antekk.tetris.game.tetrominos;

import com.antekk.tetris.game.shapes.Shape;
import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SquareShape extends Shape {

    @Override
    public ArrayList<Point> getDefaultCollisionPoints() {
        return new ArrayList<>(Arrays.asList(
                new Point(4,-1),
                new Point(5,-1),
                new Point(4,0),
                new Point(5,0)
        ));
    }

    @Override
    public Color getDefaultColor() {
        return Color.YELLOW;
    }

    @Override
    public void setAsHeldShape() {
        super.setAsHeldShape();
        move(-TetrisGamePanel.getBlockSizePx(), TetrisGamePanel.getBlockSizePx() * 2);
    }

    @Override
    public void setAsNextShape() {
        super.setAsNextShape();
        move(17 * TetrisGamePanel.getBlockSizePx(), 2 * TetrisGamePanel.getBlockSizePx());
    }

    @Override
    public boolean rotateLeft() {
        return false;
    }

    @Override
    public boolean rotateRight() {
        return false;
    }
}
