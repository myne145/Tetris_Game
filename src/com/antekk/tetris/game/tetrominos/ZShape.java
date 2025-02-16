package com.antekk.tetris.game.tetrominos;

import com.antekk.tetris.game.shapes.Shape;
import com.antekk.tetris.game.Shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ZShape extends Shape {

    @Override
    public void setDefaultValues() {
        collisionPoints = new ArrayList<>(Arrays.asList(
                new Point(4,1),
                new Point(3,0),
                new Point(4,0),
                new Point(5,1)
        ));
        shapeColor = Color.RED;
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
