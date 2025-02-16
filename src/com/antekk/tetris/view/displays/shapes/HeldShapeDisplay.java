package com.antekk.tetris.view.displays.shapes;

import java.awt.*;

import static com.antekk.tetris.game.Shapes.getHeldShape;
import static com.antekk.tetris.view.TetrisGamePanel.TOP;
import static com.antekk.tetris.view.TetrisGamePanel.getBlockSizePx;

public class HeldShapeDisplay extends TetrisShapeDisplay {
    public HeldShapeDisplay() {
        super(getBlockSizePx(), TOP, "HELD");
    }

    @Override
    public void drawShape(Graphics g) {
        if(getHeldShape() != null)
            getHeldShape().drawAsHeldShape(g);
    }
}
