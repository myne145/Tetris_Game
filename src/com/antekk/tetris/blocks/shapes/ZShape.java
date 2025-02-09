package com.antekk.tetris.blocks.shapes;

import com.antekk.tetris.blocks.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ZShape extends Shape {
    private final ArrayList<Point> collisionPoints = new ArrayList<>(Arrays.asList(
            new Point(4,1),
            new Point(3,0),
            new Point(4,0),
            new Point(5,1)
    ));

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public ArrayList<Point> getCollisionPoints() {
        return collisionPoints;
    }
}
