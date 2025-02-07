package com.antekk.tetris.blocks.shapes;

import com.antekk.tetris.blocks.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SShape implements Shape {
    private final ArrayList<Point> collisionPoints = new ArrayList<>(Arrays.asList(
            new Point(0,1),
            new Point(1,1),
            new Point(0,0),
            new Point(1,2)
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
