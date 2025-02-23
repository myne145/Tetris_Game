package com.antekk.tetris.game.tetrominos;

import com.antekk.tetris.game.shapes.Shape;
import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.antekk.tetris.game.Shapes.getBlockSizePx;

public class TShape extends Shape {
    private final Color stationaryColor = Color.MAGENTA;
    private final Color colorWhileMoving = new Color(255, 0, 255, 150);

    @Override
    public Color getDefaultColor() {
        return stationaryColor;
    }

    @Override
    public Color getDefaultColorForMovingShape() {
        return colorWhileMoving;
    }

    @Override
    public ArrayList<Point> getDefaultCollisionPoints() {
        return new ArrayList<>(List.of(
                new Point(4,0), //center
                new Point(3, 0),
                new Point(4, -1),
                new Point(5, 0)
        ));
    }

    @Override
    public void setAsHeldShape() {
        super.setAsHeldShape();
        move(-getBlockSizePx() / 2, getBlockSizePx() * 3);
    }

    @Override
    public void setAsNextShape() {
        super.setAsNextShape();
        move((int) (17.5 * getBlockSizePx()), 3 * getBlockSizePx());
    }
}
