package com.antekk.tetris.blocks.shapes;

import com.antekk.tetris.blocks.Block;
import com.antekk.tetris.blocks.Shape;

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
    public void setHeld() {
        setDefaultValues();
        for(Point p : collisionPoints) {
            p.x *= 50;
            p.y *= 50;
        }
        translate(-Block.getSizePx(), Block.getSizePx() * 2);
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
