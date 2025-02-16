package com.antekk.tetris.shapes.tetrominos;

import com.antekk.tetris.shapes.Shape;
import com.antekk.tetris.shapes.Shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LShape extends Shape {
    @Override
    protected void setDefaultValues() {
        collisionPoints = new ArrayList<>(Arrays.asList(
                new Point(4,1),
                new Point(3,1),
                new Point(5,1),
                new Point(5,0)
        ));
        shapeColor = Color.ORANGE;
    }

    @Override
    public void setAsHeldShape() {
        super.setAsHeldShape();
        move(-Shapes.getBlockSizePx() / 2, Shapes.getBlockSizePx() * 2);
    }

    @Override
    public void setAsNextShape() {
        super.setAsNextShape();
        move((int) (17.5 * Shapes.getBlockSizePx()), 2 * Shapes.getBlockSizePx());
    }
}
