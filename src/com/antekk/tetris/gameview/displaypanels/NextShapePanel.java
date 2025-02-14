package com.antekk.tetris.gameview.displaypanels;

import java.awt.*;
import com.antekk.tetris.shapes.Shape;
import com.antekk.tetris.shapes.Shapes;
import static com.antekk.tetris.gameview.GamePanel.*;

public class NextShapePanel extends ShapeDisplayPanel {
    public NextShapePanel() {
        super(LEFT + RIGHT + Shapes.getBlockSizePx(), TOP, "NEXT");
    }

    @Override
    public void drawShape(Graphics g) {
        Shape next = Shapes.getNextShape();
        next.drawAsNextShape(g);
    }
}
