package com.antekk.tetris.blocks.shapes;

import com.antekk.tetris.blocks.Shape;
import com.antekk.tetris.blocks.Shapes;

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
        super.setHeld();
        move(-Shapes.getBlockSizePx(), Shapes.getBlockSizePx() * 2);
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
