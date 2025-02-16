package com.antekk.tetris.view.displays.shapes;

import java.awt.*;
import com.antekk.tetris.game.shapes.Shape;
import com.antekk.tetris.game.Shapes;
import static com.antekk.tetris.view.TetrisGamePanel.*;

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
