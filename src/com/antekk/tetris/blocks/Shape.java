package com.antekk.tetris.blocks;

import com.antekk.tetris.gameview.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public interface Shape {
    ArrayList<Shape> stationaryShapes = new ArrayList<>();

    Color getColor();
    ArrayList<Point> getCollisionPoints();
    int rotation = 0;
    void rotateLeft();
    void rotateRight();

    default void hardDrop() {


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
        return 3f;
    }

    static ArrayList<Shape> getStationaryShapes() {
        return stationaryShapes;
    }
}
