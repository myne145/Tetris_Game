package com.antekk.tetris.blocks;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestShape implements Shape {
    private final ArrayList<ArrayList<Block>> blocks = new ArrayList<>(List.of(
            new ArrayList<>(List.of(new Block(0,0))),
            new ArrayList<>(List.of(new Block(0,1), new Block(1,1))),
            new ArrayList<>(List.of(new Block(0,2)))
    ));

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public ArrayList<ArrayList<Block>> getBlocks() {
        return blocks;
    }

    @Override
    public void rotateLeft() {

    }

    @Override
    public void rotateRight() {

    }

    @Override
    public void hardDrop() {

    }


}
