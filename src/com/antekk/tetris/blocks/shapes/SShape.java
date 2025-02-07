package com.antekk.tetris.blocks.shapes;

import com.antekk.tetris.blocks.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SShape implements Shape {
    private final ArrayList<Point> collisionPoints = new ArrayList<>(Arrays.asList(
            new Point(4,1),
            new Point(3,1),
            new Point(4,0),
            new Point(5,0)
    ));
    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    public ArrayList<Point> getCollisionPoints() {
        return collisionPoints;
    }
}
