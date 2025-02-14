package com.antekk.tetris.shapes;

import com.antekk.tetris.gameview.GamePanel;

import java.awt.*;
import java.util.ArrayList;

import static com.antekk.tetris.shapes.Shapes.getStationaryShapes;

public abstract class Shape implements Cloneable, HeldShape, NextShape {
    protected ArrayList<Point> collisionPoints;
    protected Color shapeColor;

    protected abstract void setDefaultValues();

    public Shape() {
        setDefaultValues();
    }

    public boolean rotateLeft() {
        return rotate(-1);
    }

    public boolean rotateRight() {
        return rotate(1);
    }

    public boolean moveLeft() {
        return moveHorizontaly(-1);
    }

    public boolean moveRight() {
        return moveHorizontaly(1);
    }

    public void hardDrop() {
        while(moveVertically());
    }

    public static float getSpeedBlocksPerSeconds() {
        return 2f;
    }

    public boolean moveDown() {
        return moveVertically();
    }

    public void draw(Graphics g) {
        for(Point p : getCollisionPoints()) {
            //Fill
            g.setColor(this.getColor());
            g.fillRect(GamePanel.LEFT + p.x * Shapes.getBlockSizePx(), GamePanel.TOP + p.y * Shapes.getBlockSizePx(), Shapes.getBlockSizePx(), Shapes.getBlockSizePx());

            //Border
            g.setColor(Color.BLACK);
            g.drawRect(GamePanel.LEFT + p.x * Shapes.getBlockSizePx(), GamePanel.TOP + p.y * Shapes.getBlockSizePx(), Shapes.getBlockSizePx(), Shapes.getBlockSizePx());
        }
    }

    protected boolean handleWallKicks() {
        //TODO: not finished
        //Wall kicks
        int wallKickDistanceX = 0;
        for(Point p : getCollisionPoints()) {
            int distAbs = Math.abs(p.x);
            if(p.x < 0 && distAbs > wallKickDistanceX) {
                wallKickDistanceX = distAbs;
                continue;
            }

            if(p.x >= GamePanel.getBoardCols() && -(distAbs - GamePanel.getBoardCols() + 1) < wallKickDistanceX) {
                wallKickDistanceX = -(distAbs - GamePanel.getBoardCols() + 1);
            }
        }

        move(wallKickDistanceX, 0);

        ArrayList<Point> collisions = getCollisionsForPoint(getCenterPoint());
        int maxDistance= 0;
        int distance = 0;

        for(Point p : collisions) {
            distance = getCenterPoint().x - p.x;
            if(Math.abs(distance) > maxDistance) {
                maxDistance = Math.abs(distance);
            }
        }

        if(distance < 0)
            maxDistance *= -1;

        for(Point p : getCollisionPoints()) {
            if(p.x + maxDistance < 0)
                return false;
        }

        move(maxDistance, 0);
        return true;
    }

    /**
     *
     * @param direction 1 is right, -1 is left
     */
    protected boolean rotate(int direction) {
        Shape futureShape = (Shape) this.clone();

        //Shape rotation
        Point center = (Point) futureShape.getCenterPoint().clone();
        for(Point p : futureShape.getCollisionPoints()) {
            int temp = p.y;
            p.y = p.x;
            p.x = temp;
        }

        for(Point p : futureShape.getCollisionPoints()) {
            if(direction == 1) p.x = 3 - p.x;
            else if(direction == -1) p.y = 3 - p.y;
        }

        //Restoring original position
        int dx = center.x - futureShape.getCenterPoint().x;
        int dy = center.y - futureShape.getCenterPoint().y;

        futureShape.move(dx, dy);

        if(!futureShape.handleWallKicks())
            return false;


//        if(!futureShape.checkForCollisionsForShapeXAxis().isEmpty())
//            return false;

        this.collisionPoints = futureShape.collisionPoints;

        return true;
    }

    private ArrayList<Point> getCollisionsForPoint(Point p) {
        ArrayList<Point> points = new ArrayList<>();
        for(Shape shape : getStationaryShapes()) {
            for (Point pointToCheck : shape.getCollisionPoints()) {
                if (p.x + 1 == pointToCheck.x && p.y == pointToCheck.y) {
                    points.add(pointToCheck);
                }
                if (p.x - 1 == pointToCheck.x && p.y == pointToCheck.y) {
                    points.add(pointToCheck);
                }
            }
        }
        return points;
    }

    private ArrayList<Point> checkForCollisionsForShapeXAxis() {
        ArrayList<Point> points = new ArrayList<>();
        for(Point p : getCollisionPoints()) {
            points.addAll(getCollisionsForPoint(p));
        }

        return points;
    }

    private boolean checkForCollisionsForShapeYAxis(Shape shapeToCompare) {
        for(Point pointToCheck : shapeToCompare.getCollisionPoints()) {
            for (Point p : getCollisionPoints()) {
                if (p.y + 1 == pointToCheck.y && p.x == pointToCheck.x) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean moveHorizontaly(int amount) {
        for(Point p : getCollisionPoints()) {
            if((p.x == GamePanel.getBoardCols() - 1 && amount > 0) ||
                    (p.x == 0 && amount < 0)) {
                return false;
            }
        }

        if(!checkForCollisionsForShapeXAxis().isEmpty())
            return false;

        move(amount, 0);
        return true;
    }

    public boolean moveVertically() {
        for(Point p : getCollisionPoints()) {
            if(p.y == GamePanel.getBoardRows() - 1) {
                return false;
            }
        }

        for(Shape shape : getStationaryShapes()) {
            if(checkForCollisionsForShapeYAxis(shape))
                return false;
        }

        this.move(0,1);
        return true;
    }

    protected void move(int dx, int dy) {
        for(Point p : getCollisionPoints()) {
            p.translate(dx, dy);
        }
    }

    private void multiplyPoints() {
        setDefaultValues();
        for(Point p : getCollisionPoints()) {
            p.x *= Shapes.getBlockSizePx();
            p.y *= Shapes.getBlockSizePx();
        }
    }

    @Override
    public void setHeld() {
        multiplyPoints();
    }

    @Override
    public void setAsNextShape() {
        multiplyPoints();
    }

    public ArrayList<Point> getCollisionPoints() {
        return collisionPoints;
    }

    public Color getColor() {
        return shapeColor;
    }

    public Point getCenterPoint() {
        return getCollisionPoints().getFirst();
    }

    @Override
    protected Object clone() {
        Shape shape;
        try {
            shape = (Shape) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        shape.shapeColor = getColor();
        shape.collisionPoints = new ArrayList<>();
        for(Point p : getCollisionPoints())
            shape.collisionPoints.add(new Point(p.x, p.y));

        return shape;
    }
}
