package com.antekk.tetris.blocks.shapes;

import com.antekk.tetris.blocks.Shape;
import com.antekk.tetris.blocks.Shapes;
import com.antekk.tetris.gameview.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LineShape extends Shape {
    private int rotationState = 0; //0,1,2,3
    private static String debugLine = "Rotation state: 0";

    @Override
    protected void setDefaultValues() {
        collisionPoints = new ArrayList<>(Arrays.asList(
                new Point(5,0),
                new Point(4,0),
                new Point(3,0),
                new Point(6,0)
        ));

        rotationState = 0;
        shapeColor = Color.CYAN;
    }

    @Override
    public void setHeld() {
        super.setHeld();
        move(-Shapes.getBlockSizePx(), Shapes.getBlockSizePx());
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(Color.RED);
        g.fillRect(GamePanel.LEFT + Shapes.getBlockSizePx() * (getCenterPoint().x) - 5, GamePanel.TOP + Shapes.getBlockSizePx() * (getCenterPoint().y) - 5, 10, 10);

        g.drawString(debugLine, 100, 500);
    }

    @Override
    public Point getCenterPoint() {
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

        debugLine = "Rotation state: " + rotationState;



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
