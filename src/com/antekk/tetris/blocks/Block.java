package com.antekk.tetris.blocks;

import java.awt.*;

public class Block extends Rectangle {

    public Block(int blockPosX, int blockPosY) {
        super(blockPosX * getSizePx(), blockPosY * getSizePx(), getSizePx(), getSizePx());
    }

    public static int getSizePx() {
        return 50;
    }
}
