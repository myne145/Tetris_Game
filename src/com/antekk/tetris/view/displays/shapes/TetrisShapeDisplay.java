package com.antekk.tetris.view.displays.shapes;

import com.antekk.tetris.view.displays.TetrisDisplay;

import java.awt.*;

abstract class TetrisShapeDisplay extends TetrisDisplay {

    abstract void drawShape(Graphics g);

    @Override
    public void drawDisplay(Graphics g) {
        super.drawDisplay(g);
        drawShape(g);
    }

    protected TetrisShapeDisplay(int x, int y, String text) {
        super(x, y, text);
    }
}
