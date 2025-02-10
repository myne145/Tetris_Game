package com.antekk.tetris.blocks;

import com.antekk.tetris.gameview.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public abstract class Shape implements Cloneable{
    private static final ArrayList<Shape> stationaryShapes = new ArrayList<>();
    protected ArrayList<Point> collisionPoints;
    protected Color shapeColor;

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
        while(moveDown());
    }

    public static float getSpeedBlocksPerSeconds() {
        return 2f;
    }

    public boolean moveDown() {
        for(Point p : getCollisionPoints()) {
            if(p.y == GamePanel.getBoardRows() - 1) {
                return false;
            }
        }

        for(Shape shape : getStationaryShapes()) {
            if(checkForCollisionsForShapeYAxis(shape))
                return false;
        }

        this.translate(0,1);
        return true;
    }

    public void draw(Graphics g) {
        for(Point p : getCollisionPoints()) {
            //Fill
            g.setColor(this.getColor());
            g.fillRect(GamePanel.LEFT + p.x * Block.getSizePx(), GamePanel.TOP + p.y * Block.getSizePx(), Block.getSizePx(), Block.getSizePx());

            //Border
            g.setColor(Color.BLACK);
            g.drawRect(GamePanel.LEFT + p.x * Block.getSizePx(), GamePanel.TOP + p.y * Block.getSizePx(), Block.getSizePx(), Block.getSizePx());
        }
    }

    /**
     *
     * @param direction 1 is right, -1 is left
     */
    private boolean rotate(int direction) {
        Shape futureShape;
        try {
            futureShape = (Shape) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException();
        }

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

        for(Point p : futureShape.getCollisionPoints()) {
            p.translate(dx, dy);
        }

        //TODO: not finished
        //Wall kicks
        int wallKickDistanceX = 0;
        for(Point p : futureShape.getCollisionPoints()) {
            int distAbs = Math.abs(p.x);
            if(p.x < 0 && distAbs > wallKickDistanceX) {
                wallKickDistanceX = distAbs;
                continue;
            }

            if(p.x >= GamePanel.getBoardCols() && -(distAbs - GamePanel.getBoardCols() + 1) < wallKickDistanceX) {
                wallKickDistanceX = -(distAbs - GamePanel.getBoardCols() + 1);
            }
        }

        futureShape.translate(wallKickDistanceX, 0);

        ArrayList<Point> collisions = futureShape.getCollisionsForPoint(futureShape.getCenterPoint());
        int maxDistance= 0;
        int distance = 0;

        for(Point p : collisions) {
            distance = futureShape.getCenterPoint().x - p.x;
            if(Math.abs(distance) > maxDistance) {
                maxDistance = Math.abs(distance);
            }
        }

        if(distance < 0)
            maxDistance *= -1;

        futureShape.translate(maxDistance, 0);


        if(!futureShape.checkForCollisionsForShapeXAxis().isEmpty())
            return false;

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

    /**
     *
     * @param direction -1 is left, 1 is right
     * @return
     */
    private boolean moveHorizontaly(int direction) {
        for(Point p : getCollisionPoints()) {
            if((p.x == GamePanel.getBoardCols() - 1 && direction == 1) ||
                    (p.x == 0 && direction == -1)) {
                return false;
            }
        }

        if(!checkForCollisionsForShapeXAxis().isEmpty())
            return false;

        translate(direction, 0);
        return true;
    }

    private void translate(int dx, int dy) {
        for(Point p : getCollisionPoints()) {
            p.translate(dx, dy);
        }
    }

    public static ArrayList<Shape> getStationaryShapes() {
        return stationaryShapes;
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
    protected Object clone() throws CloneNotSupportedException {
        Shape shape = (Shape) super.clone();
        shape.shapeColor = getColor();
        shape.collisionPoints = new ArrayList<>();
        for(Point p : getCollisionPoints())
            shape.collisionPoints.add(new Point(p.x, p.y));

        return shape;
    }
}
