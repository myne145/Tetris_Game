package com.antekk.tetris.game.tetrominos;

import com.antekk.tetris.game.shapes.Shape;
import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LineShape extends Shape {
    private int rotationState = 0; //0,1,2,3
//    private static String debugLine = "Rotation state: 0";


    @Override
    public ArrayList<Point> getDefaultCollisionPoints() {
        return new ArrayList<>(Arrays.asList(
                new Point(5,-1),
                new Point(4,-1),
                new Point(3,-1),
                new Point(6,-1)
        ));
    }

    @Override
    public Color getDefaultColor() {
        return Color.CYAN;
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
        move(-TetrisGamePanel.getBlockSizePx(), 2 * TetrisGamePanel.getBlockSizePx());
    }

    @Override
    public void setAsNextShape() {
        super.setAsNextShape();
        move(17 * TetrisGamePanel.getBlockSizePx(), 2 * TetrisGamePanel.getBlockSizePx());
    }

//    @Override
//    public void draw(Graphics g) {
//        super.draw(g);
//        g.setColor(Color.RED);
//        g.fillRect(TetrisGamePanel.LEFT + Shapes.getBlockSizePx() * (getCenterPoint().x) - 5, TetrisGamePanel.TOP + Shapes.getBlockSizePx() * (getCenterPoint().y) - 5, 10, 10);
//
////        g.drawString(debugLine, 100, 500);
//    }

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
        rotationState += direction;
        if(rotationState == -1)
            rotationState = 3;
        else if(rotationState == 4)
            rotationState = 0;

//        debugLine = "Rotation state: " + rotationState;



        if(rotationState == 0)
            move(1, 0);

        if(rotationState == 1)
            move(0, 1);

        if(rotationState == 2)
            move(-1, 0);

        if(rotationState == 3)
            move(0, -1);
//        return true;
        return super.rotate(direction);
    }
}
