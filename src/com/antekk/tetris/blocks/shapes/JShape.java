package com.antekk.tetris.blocks.shapes;

import com.antekk.tetris.blocks.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class JShape implements Shape {
    private final ArrayList<Point> collisionPoints = new ArrayList<>(Arrays.asList(
            new Point(1,0),
            new Point(1,1),
            new Point(1,2),
            new Point(0, 2)
    ));

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public ArrayList<Point> getCollisionPoints() {
        return collisionPoints;
    }

    @Override
    public void rotateLeft() {

    }

    @Override
    public void rotateRight() {

    }
}
