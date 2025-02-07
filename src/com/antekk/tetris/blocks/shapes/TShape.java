package com.antekk.tetris.blocks.shapes;

import com.antekk.tetris.blocks.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TShape implements Shape {
    private final ArrayList<Point> collisionPoints = new ArrayList<>(List.of(
            new Point(1,1), //center
            new Point(1, 0),
            new Point(0, 1),
            new Point(2, 1)
    ));

    @Override
    public Color getColor() {
        return Color.MAGENTA;
    }

    @Override
    public ArrayList<Point> getCollisionPoints() {
        return collisionPoints;
    }

//    @Override
//    public void rotateLeft() {
//        Point center = (Point) collisionPoints.get(2).clone();
//        for(Point p : getCollisionPoints()) {
//            int temp = p.y;
//            p.y = p.x;
//            p.x = temp;
//        }
//
//        for(Point p : getCollisionPoints()) {
//            p.y = 3 - p.y;
//        }
//
//        int dx = center.x - collisionPoints.get(2).x;
//        int dy = center.y - collisionPoints.get(2).y;
//
//        for(Point p : getCollisionPoints()) {
//            p.translate(dx, dy);
//        }
//    }
//
//    @Override
//    public void rotateRight() {
//        Point center = (Point) collisionPoints.get(2).clone();
//        for(Point p : getCollisionPoints()) {
//            int temp = p.y;
//            p.y = p.x;
//            p.x = temp;
//        }
//
//        for(Point p : getCollisionPoints()) {
//            p.x = 3 - p.x;
//        }
//
//        int dx = center.x - collisionPoints.get(2).x;
//        int dy = center.y - collisionPoints.get(2).y;
//
//        for(Point p : getCollisionPoints()) {
//            p.translate(dx, dy);
//        }
//
//    }
}
