package com.antekk.tetris.game.tetrominos;

import com.antekk.tetris.game.shapes.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static com.antekk.tetris.game.Shapes.getBlockSizePx;

public class JShape extends Shape {
    protected Color stationaryColor = Color.BLUE;
    protected Color colorWhileMoving = new Color(0, 0, 255, 150);

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
        return stationaryColor;
    }

    @Override
    public Color getDefaultColorForMovingShape() {
        return colorWhileMoving;
    }


    @Override
    public void setAsHeldShape() {
        super.setAsHeldShape();
        move(-getBlockSizePx() / 2, getBlockSizePx() * 2);
    }

    @Override
    public void setAsNextShape() {
        super.setAsNextShape();
        move((int) (17.5 * getBlockSizePx()), 2 * getBlockSizePx());
    }
}
