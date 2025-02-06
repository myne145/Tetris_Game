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
        for(int i = 0; i < getCollisionPoints().size(); i++) {
            for(int j = 0; j < getCollisionPoints().size(); j++) {

            }
        }


        for(Point p : getCollisionPoints()) {
            //Fill
            g.setColor(this.getColor());
            g.fillRect(p.x * Block.getSizePx(), p.y * Block.getSizePx(), Block.getSizePx(), Block.getSizePx());

            //Border
            g.setColor(Color.BLACK);
            g.drawRect(p.x * Block.getSizePx(), p.y * Block.getSizePx(), Block.getSizePx(), Block.getSizePx());
        }
    }

    default boolean moveDown() {
        for(Point p : getCollisionPoints()) {
            if(p.y == GamePanel.getBoardRows() - 1) {
                return false;
            }
        }
        for(Shape shape : getStationaryShapes()) {
            for(Point checkedShapesCollisionPoint : shape.getCollisionPoints()) {
                for (Point p : getCollisionPoints()) {
                    if (p.y + 1 == checkedShapesCollisionPoint.y && p.x == checkedShapesCollisionPoint.x) {
                        return false;
                    }
                }
            }
        }
//        if(getBottomBlock().y == (GamePanel.getBoardRows() - 1) * Block.getSizePx()) {
//            return false;
//        }

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
//        if(getBottomBlock().x == (GamePanel.getBoardRows() - 1) * Block.getSizePx()) {
//            return false;
//        } //TODO

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
