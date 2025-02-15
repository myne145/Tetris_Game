package com.antekk.tetris.shapes;

import com.antekk.tetris.gameview.GamePanel;
import com.antekk.tetris.shapes.tetrominos.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Shapes {
    private static final ArrayList<Shape> shapesList = new ArrayList<>();
    private static final ArrayList<Shape> stationaryShapes = new ArrayList<>();
    private static Shape heldShape;
    private static Shape currentShape;
    private static boolean wasHeldUsed = false;
    public static Shape shadow;

    public static Shape getRandomizedShape() {
        if(shapesList.size() == 1 || shapesList.isEmpty()) {
            //filling the array
            shapesList.add(new TShape());
            shapesList.add(new ZShape());
            shapesList.add(new JShape());
            shapesList.add(new LShape());
            shapesList.add(new LineShape());
            shapesList.add(new SquareShape());
            shapesList.add(new SShape());

            Shape firstShape = shapesList.getFirst();
            shapesList.removeFirst();

            //randomly shifts elements left or right
            shapesList.sort((o1, o2) -> (int) (Math.random() * 2) - 1);
            shapesList.addFirst(firstShape);

            if(shapesList.get(0).getClass() == shapesList.get(1).getClass()) {
                int rand = (int) (Math.random() * 6) + 1;
                Shape temp = shapesList.get(rand);
                shapesList.set(rand, shapesList.get(1));
                shapesList.set(1, temp);
            }
        }

        Shape temp = shapesList.getFirst();
        shapesList.removeFirst();
        temp.setDefaultValues();
        return temp;
    }

    private static Shape updateHeldShape(Shape currentShape) {
        if(wasHeldUsed)
            return currentShape;

        Shape returnValue;
        if(heldShape == null)
            returnValue = getRandomizedShape();
        else
            returnValue = heldShape;

        currentShape.setHeld();
        heldShape = currentShape;

        returnValue.setDefaultValues();
        lockHeld();
        return returnValue;
    }

    public static void clearFullLines() {
        int start = 0;
        int end = Integer.MAX_VALUE;
        for(int currentYpos = 0; currentYpos < GamePanel.getBoardRows(); currentYpos++) {
            boolean isLineFull = isLineFull(currentYpos);
            if(isLineFull && currentYpos > start) {
                start = currentYpos;
            }
            if(isLineFull && currentYpos < end) {
                end = currentYpos;
            }
        }

        if(end == Integer.MAX_VALUE)
            return;
        clearLineAt(start, end);
    }

    private static boolean isLineFull(int y) {
        ArrayList<Integer> xPositions = new ArrayList<>();

        for(Shape shape : getStationaryShapes()) {
            for(Point p : shape.getCollisionPoints()) {
                if(p.y == y)
                    xPositions.add(p.x);
            }
        }

        return xPositions.size() == GamePanel.getBoardCols();
    }

    private static void clearLineAt(int yStart, int yEnd) {
        if(yStart > yEnd) {
            int temp = yEnd;
            yEnd = yStart;
            yStart = temp;
        }

        for(Shape shape : (ArrayList<Shape>) getStationaryShapes().clone()) {
            ArrayList<Point> temp = (ArrayList<Point>) shape.getCollisionPoints().clone();
            for(Point p : temp) {
                if(p.y >= yStart && p.y <= yEnd) {
                    shape.getCollisionPoints().remove(p);
                }
            }
            if(shape.getCollisionPoints().isEmpty())
                stationaryShapes.remove(shape);
        }

        int distanceToMoveLinesDownBy = yEnd - yStart + 1;
        for(Shape shape : getStationaryShapes()) {
            for(Point p : shape.getCollisionPoints()) {
                if(p.y <= yStart) {
                    p.translate(0, distanceToMoveLinesDownBy);
                }

            }
        }

    }

    public static void swapHeldAndCurrentShapes() {
        currentShape = updateHeldShape(currentShape);
        shadow = (Shape) currentShape.clone();
    }

    public static Shape getCurrentShape() {
        return currentShape;
    }

    public static void updateCurrentShape() {
        currentShape = getRandomizedShape();
        shadow = (Shape) currentShape.clone();

        shadow.reloadShadow();
        while(shadow.moveDown());
    }

    public static Shape getHeldShape() {
        return heldShape;
    }

    public static ArrayList<Shape> getStationaryShapes() {
        return stationaryShapes;
    }

    public static void lockHeld() {
        wasHeldUsed = true;
    }

    public static void unlockHeld() {
        wasHeldUsed = false;
    }

    public static Shape getNextShape() {
        shapesList.getFirst().setAsNextShape();
        return shapesList.getFirst();
    }

    public static int getBlockSizePx() {
        return 40;
    }
}
