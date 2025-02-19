package com.antekk.tetris.game.tetrominos;

import com.antekk.tetris.game.shapes.Shape;
import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static com.antekk.tetris.game.Shapes.getBlockSizePx;

public class LineShape extends Shape {
    private int rotationState = 0; //0,1,2,3
    private final Color stationaryColor = Color.CYAN;
    private final Color colorWhileMoving = new Color(0, 255, 255, 150);

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
        return new ArrayList<>(Arrays.asList(
                new Point(5,0),
                new Point(4,0),
                new Point(3,0),
                new Point(6,0)
        ));
    }

    public int getDefaultRotationState() {
        return 0;
    }

    @Override
    public void setDefaultValues() {
        super.setDefaultValues();
        rotationState = getDefaultRotationState();
    }

    @Override
    public void setAsHeldShape() {
        super.setAsHeldShape();
        move(-getBlockSizePx(), 2 * getBlockSizePx());
    }

    @Override
    public void setAsNextShape() {
        super.setAsNextShape();
        move(17 * getBlockSizePx(), 2 * getBlockSizePx());
    }

    @Override
    public Point getCenterPoint() {
        if(collisionPoints.isEmpty())
            return new Point(0, 0);

        if(rotationState == 0)
            return new Point(getCollisionPoints().get(0).x,getCollisionPoints().get(0).y + 1);
        else if (rotationState == 1)
            return new Point(getCollisionPoints().get(0).x,getCollisionPoints().get(0).y);
        else if (rotationState == 2)
            return new Point(getCollisionPoints().get(0).x + 1,getCollisionPoints().get(0).y);
        else if (rotationState == 3)
            return new Point(getCollisionPoints().get(0).x + 1,getCollisionPoints().get(0).y + 1);

        return super.getCenterPoint();
    }

    @Override
    protected boolean rotate(int direction) {
        LineShape clone = (LineShape) this.clone();

        rotationState += direction;
        if(rotationState == -1)
            rotationState = 3;
        else if(rotationState == 4)
            rotationState = 0;


        if(rotationState == 0)
            move(1, 0);

        if(rotationState == 1)
            move(0, 1);

        if(rotationState == 2)
            move(-1, 0);

        if(rotationState == 3)
            move(0, -1);
//        return true;

        boolean canRotate = super.rotate(direction);

        if(!canRotate) {
            this.rotationState = clone.rotationState;
            this.collisionPoints = clone.collisionPoints;
            return false;
        }

        return true;
    }
}
