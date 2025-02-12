package com.antekk.tetris.blocks.shapes;

import com.antekk.tetris.blocks.Shape;
import com.antekk.tetris.blocks.Shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TShape extends Shape {

    @Override
    protected void setDefaultValues() {
        collisionPoints = new ArrayList<>(List.of(
                new Point(4,1), //center
                new Point(3, 1),
                new Point(4, 0),
                new Point(5, 1)
        ));
        shapeColor = Color.MAGENTA;
    }

    @Override
    public void setHeld() {
        super.setHeld();
        move(-Shapes.getBlockSizePx() / 2, Shapes.getBlockSizePx() * 2);
    }
}
