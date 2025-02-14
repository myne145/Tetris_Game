package com.antekk.tetris.gameview.displaypanels;

import com.antekk.tetris.shapes.Shapes;

import java.awt.*;

import static com.antekk.tetris.gameview.GamePanel.TOP;
import static com.antekk.tetris.shapes.Shapes.getBlockSizePx;

public class HeldShapePanel extends ShapeDisplayPanel{
    public HeldShapePanel() {
        super(getBlockSizePx(), TOP, "HELD");
    }

    @Override
    public void drawShape(Graphics g) {
        if(Shapes.getHeldShape() != null)
            Shapes.getHeldShape().drawHeldShape(g);
    }
}
