package com.antekk.tetris.gameview.displays.shapes;

import java.awt.*;
import com.antekk.tetris.shapes.Shape;
import com.antekk.tetris.shapes.Shapes;
import static com.antekk.tetris.gameview.TetrisGamePanel.*;

public class NextShapeDisplay extends TetrisShapeDisplay {
    public NextShapeDisplay() {
        super(LEFT + RIGHT + Shapes.getBlockSizePx(), TOP, "NEXT");
    }

    @Override
    public void drawShape(Graphics g) {
        Shape next = Shapes.getNextShape();
        next.drawAsNextShape(g);
    }
}
