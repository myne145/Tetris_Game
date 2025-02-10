package com.antekk.tetris.blocks.shapes;

import com.antekk.tetris.blocks.Block;
import com.antekk.tetris.blocks.Shape;

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
        setDefaultValues();
        for(Point p : collisionPoints) {
            p.x *= 50;
            p.y *= 50;
        }
        translate(-Block.getSizePx() / 2, Block.getSizePx() * 2);
    }
}
