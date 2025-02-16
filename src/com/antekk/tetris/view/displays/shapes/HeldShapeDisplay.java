package com.antekk.tetris.view.displays.shapes;

import com.antekk.tetris.game.Shapes;

import java.awt.*;

import static com.antekk.tetris.view.TetrisGamePanel.TOP;
import static com.antekk.tetris.game.Shapes.getBlockSizePx;

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
