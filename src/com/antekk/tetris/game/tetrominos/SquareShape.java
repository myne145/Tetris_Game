package com.antekk.tetris.game.tetrominos;

import com.antekk.tetris.game.shapes.Shape;
import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static com.antekk.tetris.game.Shapes.getBlockSizePx;

public class SquareShape extends Shape {
    private final Color stationaryColor = Color.YELLOW;
    private final Color colorWhileMoving = new Color(255, 255, 150);

    @Override
    public Color getDefaultColor() {
        return stationaryColor;
    }

    @Override
    public Color getDefaultColorForMovingShape() {
        return colorWhileMoving;
    }

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
    public void setAsHeldShape() {
        super.setAsHeldShape();
        move(-getBlockSizePx(), getBlockSizePx() * 3);
    }

    @Override
    public void setAsNextShape() {
        super.setAsNextShape();
        move(17 * getBlockSizePx(), 3 * getBlockSizePx());
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
