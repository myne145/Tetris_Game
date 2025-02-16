package com.antekk.tetris.game.tetrominos;

import com.antekk.tetris.game.shapes.Shape;
import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TShape extends Shape {

    @Override
    public ArrayList<Point> getDefaultCollisionPoints() {
        return new ArrayList<>(List.of(
                new Point(4,0), //center
                new Point(3, 0),
                new Point(4, -1),
                new Point(5, 0)
        ));
    }

    @Override
    public Color getDefaultColor() {
        return Color.MAGENTA;
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
