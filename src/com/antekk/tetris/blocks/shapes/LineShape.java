package com.antekk.tetris.blocks.shapes;

import com.antekk.tetris.blocks.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LineShape implements Shape {
    //i hate this code :)
    private final ArrayList<Point> collisionPointsVerticalRight = new ArrayList<>(Arrays.asList(
            new Point(1,2),
            new Point(1,0),
            new Point(1,1),
            new Point(1,3)
    ));

    private final ArrayList<Point> collisionPointsVerticalLeft = new ArrayList<>(Arrays.asList(
            new Point(0,2),
            new Point(0,0),
            new Point(0,1),
            new Point(0,3)
    ));

    private final ArrayList<Point> collisionPointsHorizontalTop = new ArrayList<>(Arrays.asList(
            new Point(1,1),
            new Point(-1,1),
            new Point(0,1),
            new Point(2,1)
    ));

    private final ArrayList<Point> collisionPointsHorizontalBottom = new ArrayList<>(Arrays.asList(
            new Point(1,2),
            new Point(-1,2),
            new Point(0,2),
            new Point(2,2)
    ));

    private final ArrayList<ArrayList<Point>> shapeVariants = new ArrayList<>(Arrays.asList(
            collisionPointsVerticalRight, collisionPointsHorizontalBottom, collisionPointsVerticalLeft,
                collisionPointsHorizontalTop
    ));

    //0,1,2,3
    private int rotationState = 0;

    private void translateAllVariantsBy(int dx, int dy) {
        for(int i = 0; i < shapeVariants.size(); i++) {
            if(i == rotationState)
                continue;
            for(Point p : shapeVariants.get(i)) {
                p.translate(dx, dy);
            }
        }
    }

    /**
     *
     * @param direction -1 is left, 1 is right
     */
    private boolean rotate(int direction) {
        rotationState += direction;
        if(rotationState == -1)
            rotationState = 3;
        else if(rotationState == 4)
            rotationState = 0;

        Point center = (Point) getCollisionPoints().get(0).clone();

        int dx = center.x - getCollisionPoints().get(0).x;
        int dy = center.y - getCollisionPoints().get(0).y;

        for(Point p : getCollisionPoints()) {
            p.translate(dx, dy);
        }
        return true;
    }

    @Override
    public Color getColor() {
        return Color.CYAN;
    }

    @Override
    public ArrayList<Point> getCollisionPoints() {
        if(rotationState >= 0 && rotationState <= 3)
            return shapeVariants.get(rotationState);

        throw new IllegalArgumentException("Rotation state must be between 0 and 3!");
    }

    /**
     * Translates the points from other variants too
     * @return
     */
    @Override
    public boolean moveDown() {
        boolean canMoveDown = Shape.super.moveDown();
        if(!canMoveDown)
            return false;

        translateAllVariantsBy(0, 1);
        return true;
    }

    /**
     * Translates the points from other variants too
     * @return
     */
    @Override
    public boolean moveLeft() {
        boolean canMoveLeft = Shape.super.moveLeft();
        if(!canMoveLeft)
            return false;

        translateAllVariantsBy(-1, 0);
        return true;
    }

    /**
     * Translates the points from other variants too
     * @return
     */
    @Override
    public boolean moveRight() {
        boolean canMoveRight = Shape.super.moveRight();
        if(!canMoveRight)
            return false;

        translateAllVariantsBy(1, 0);
        return true;
    }

    @Override
    public boolean rotateLeft() {
        return rotate(-1);
    }

    @Override
    public boolean rotateRight() {
        return rotate(1);
    }
}
