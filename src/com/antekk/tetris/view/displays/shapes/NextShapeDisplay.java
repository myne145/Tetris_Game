package com.antekk.tetris.view.displays.shapes;

import java.awt.*;
import com.antekk.tetris.game.shapes.Shape;

import static com.antekk.tetris.game.Shapes.getNextShape;
import static com.antekk.tetris.view.TetrisGamePanel.*;

public class NextShapeDisplay extends TetrisShapeDisplay {
    public NextShapeDisplay() {
        super(LEFT + RIGHT + getBlockSizePx(), TOP, "NEXT");
    }

    @Override
    public void drawShape(Graphics g) {
        getNextShape().drawAsNextShape(g);
    }
}
