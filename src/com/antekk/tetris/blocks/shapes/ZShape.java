package com.antekk.tetris.blocks.shapes;

import com.antekk.tetris.blocks.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ZShape implements Shape {
    private final ArrayList<Point> collisionPoints = new ArrayList<>(Arrays.asList(
            new Point(0,0),
            new Point(1,0),
            new Point(1,1),
            new Point(2,1)
    ));

    @Override
    public Color getColor() {
        return Color.RED;
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
