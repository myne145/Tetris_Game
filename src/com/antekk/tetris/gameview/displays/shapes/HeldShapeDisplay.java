package com.antekk.tetris.gameview.displays.shapes;

import com.antekk.tetris.shapes.Shapes;

import java.awt.*;

import static com.antekk.tetris.gameview.TetrisGamePanel.TOP;
import static com.antekk.tetris.shapes.Shapes.getBlockSizePx;

public class HeldShapeDisplay extends TetrisShapeDisplay {
    public HeldShapeDisplay() {
        super(getBlockSizePx(), TOP, "HELD");
    }

    @Override
    public void drawShape(Graphics g) {
        if(Shapes.getHeldShape() != null)
            Shapes.getHeldShape().drawAsHeldShape(g);
    }
}
