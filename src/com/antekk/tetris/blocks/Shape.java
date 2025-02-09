package com.antekk.tetris.blocks;

import com.antekk.tetris.gameview.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public abstract class Shape {
    private static final ArrayList<Shape> stationaryShapes = new ArrayList<>();

    public abstract ArrayList<Point> getCollisionPoints();
    public abstract Color getColor();

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

        for(Point p : getCollisionPoints()) {
            p.translate(0, 1);
        }
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
        ArrayList<Point> futureCollisionPoints = new ArrayList<>(getCollisionPoints());

        //Shape rotation
        Point center = (Point) futureCollisionPoints.get(0).clone();
        for(Point p : futureCollisionPoints) {
            int temp = p.y;
            p.y = p.x;
            p.x = temp;
        }

        for(Point p : futureCollisionPoints) {
            if(direction == 1) p.x = 3 - p.x;
            else if(direction == -1) p.y = 3 - p.y;
        }

        //Restoring original position
        int dx = center.x - futureCollisionPoints.get(0).x;
        int dy = center.y - futureCollisionPoints.get(0).y;

        for(Point p : futureCollisionPoints) {
            p.translate(dx, dy);
        }

        //Wall kicks
        int wallKickDistanceX = 0;
        for(Point p : futureCollisionPoints) {
            int distAbs = Math.abs(p.x);
            if(p.x < 0 && distAbs > wallKickDistanceX) {
                wallKickDistanceX = distAbs;
            } else if(p.x >= GamePanel.getBoardCols() && -(distAbs - GamePanel.getBoardCols() + 1) < wallKickDistanceX) {
                wallKickDistanceX = -(distAbs - GamePanel.getBoardCols() + 1);
            }
        }

        for(Point p : futureCollisionPoints) {
            p.translate(wallKickDistanceX, 0);
        }

        //Collision check for other shapes
        for(Shape shape : Shape.getStationaryShapes()) {
            if(checkForCollisionsForShapeYAxis(shape) || checkForCollisionsForShapeXAxis(shape))
                return false;
        }

        //If all checks passed, set the new rotation
        for(int i = 0; i < futureCollisionPoints.size(); i++) {
            this.getCollisionPoints().set(i, futureCollisionPoints.get(i));
        }

        return true;
    }

    private boolean checkForCollisionsForShapeXAxis(Shape shapeToCompare) {
        for(Point pointToCheck : shapeToCompare.getCollisionPoints()) {
            for (Point p : getCollisionPoints()) {
                if (p.x + 1 == pointToCheck.x && p.y == pointToCheck.y) {
                    return true;
                }
                if (p.x - 1 == pointToCheck.x && p.y == pointToCheck.y) {
                    return true;
                }
            }
        }
        return false;
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

        for(Shape shape : getStationaryShapes()) {
            if(checkForCollisionsForShapeXAxis(shape))
                return false;
        }

        for(Point p : getCollisionPoints()) {
            p.translate(direction, 0);
        }
        return true;
    }

    public static ArrayList<Shape> getStationaryShapes() {
        return stationaryShapes;
    }
}
