package com.antekk.tetris.blocks;

import com.antekk.tetris.gameview.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public interface Shape {
    ArrayList<Shape> stationaryShapes = new ArrayList<>();

    Color getColor();

    /**
     *
     * @return list of collision points, first element is a center point
     */
    ArrayList<Point> getCollisionPoints();

    /**
     *
     * @param direction 1 is right, -1 is left
     */
    private boolean rotate(int direction) {
        ArrayList<Point> futureCollisionPoints = (ArrayList<Point>) getCollisionPoints().clone();

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

        int dx = center.x - futureCollisionPoints.get(0).x;
        int dy = center.y - futureCollisionPoints.get(0).y;

        for(Point p : futureCollisionPoints) {
            p.translate(dx, dy);
        }

        for(Shape shape : getStationaryShapes()) {
            if(checkForCollisionsForShapeYAxis(shape) || checkForCollisionsForShapeXAxis(shape))
                return false;
        }

        for(int i = 0; i < futureCollisionPoints.size(); i++) {
            this.getCollisionPoints().set(i, futureCollisionPoints.get(i));
        }
        return true;
    }

    default boolean rotateLeft() {
        return rotate(-1);
    }

    default boolean rotateRight() {
        return rotate(1);
    }



    default void hardDrop() {
        while(moveDown());
    }

    default void draw(Graphics g) {
        for(Point p : getCollisionPoints()) {
            //Fill
            g.setColor(this.getColor());
            g.fillRect(p.x * Block.getSizePx(), p.y * Block.getSizePx(), Block.getSizePx(), Block.getSizePx());

            //Border
            g.setColor(Color.BLACK);
            g.drawRect(p.x * Block.getSizePx(), p.y * Block.getSizePx(), Block.getSizePx(), Block.getSizePx());
        }
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

    default boolean moveDown() {
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

    default boolean moveLeft() {
        return moveHorizontaly(-1);
    }

    default boolean moveRight() {
        return moveHorizontaly(1);
    }

    static float getSpeedBlocksPerSeconds() {
        return 6f;
    }

    static ArrayList<Shape> getStationaryShapes() {
        return stationaryShapes;
    }
}
