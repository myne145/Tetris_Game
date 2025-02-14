package com.antekk.tetris.shapes;

import com.antekk.tetris.shapes.tetrominos.*;

import java.util.ArrayList;

public class Shapes {
    private static final ArrayList<Shape> shapesList = new ArrayList<>();
    private static final ArrayList<Shape> stationaryShapes = new ArrayList<>();
    private static Shape heldShape;
    private static Shape currentShape;
    private static boolean wasHeldUsed = false;

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

    public static Shape updateHeldShape(Shape currentShape) {
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

    public static Shape getCurrentShape() {
        return currentShape;
    }

    public static void setCurrentShape(Shape currentShape) {
        Shapes.currentShape = currentShape;
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
