package com.antekk.tetris.blocks.shapes;

import com.antekk.tetris.blocks.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class JShape extends Shape {
    private final ArrayList<Point> collisionPoints = new ArrayList<>(Arrays.asList(
            new Point(4,1),
            new Point(3,0),
            new Point(3,1),
            new Point(5, 1)
    ));

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public ArrayList<Point> getCollisionPoints() {
        return collisionPoints;
    }
}
