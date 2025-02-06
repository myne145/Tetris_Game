package com.antekk.tetris.blocks.shapes;

import com.antekk.tetris.blocks.Block;
import com.antekk.tetris.blocks.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TShape implements Shape {
    private final ArrayList<Point> blocks = new ArrayList<>(List.of(
            new Point(1, 0),
            new Point(0, 1),
            new Point(1,1),
            new Point(2, 1)
    ));

    @Override
    public Color getColor() {
        return Color.MAGENTA;
    }

    @Override
    public ArrayList<Point> getCollisionPoints() {
        return blocks;
    }

    @Override
    public void rotateLeft() {

    }

    @Override
    public void rotateRight() {

    }
}
