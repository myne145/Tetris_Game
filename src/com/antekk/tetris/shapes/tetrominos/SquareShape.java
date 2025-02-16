package com.antekk.tetris.shapes.tetrominos;

import com.antekk.tetris.shapes.Shape;
import com.antekk.tetris.shapes.Shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SquareShape extends Shape {

    @Override
    protected void setDefaultValues() {
        collisionPoints = new ArrayList<>(Arrays.asList(
                new Point(4,0),
                new Point(5,0),
                new Point(4,1),
                new Point(5,1)
        ));
        shapeColor = Color.YELLOW;
    }

    @Override
    public void setAsHeldShape() {
        super.setAsHeldShape();
        move(-Shapes.getBlockSizePx(), Shapes.getBlockSizePx() * 2);
    }

    @Override
    public void setAsNextShape() {
        super.setAsNextShape();
        move(17 * Shapes.getBlockSizePx(), 2 * Shapes.getBlockSizePx());
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
