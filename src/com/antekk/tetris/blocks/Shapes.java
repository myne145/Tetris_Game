package com.antekk.tetris.blocks;

import com.antekk.tetris.blocks.shapes.*;

import java.util.ArrayList;

public class Shapes {
    private static final ArrayList<Shape> shapesList = new ArrayList<>();
    private static final ArrayList<Shape> stationaryShapes = new ArrayList<>();
    private static Shape heldShape;
    private static Shape currentShape;
    private static boolean wasHeldUsed = false;
    private static Shape shadow;

    public static Shape getRandomizedShape(Shape previousShape) {
        if(shapesList.isEmpty()) {
            //filling the array
            shapesList.add(new TShape());
            shapesList.add(new ZShape());
            shapesList.add(new JShape());
            shapesList.add(new LShape());
            shapesList.add(new LineShape());
            shapesList.add(new SquareShape());
            shapesList.add(new SShape());

            //randomly shifts elements left or right
            shapesList.sort((o1, o2) -> (int) (Math.random() * 2) - 1);


            //if previous and newly generated shapes are the same swap it with some other shape
            if(previousShape != null && shapesList.getFirst().getClass() == previousShape.getClass()) {
                int rand = 0;
                while(rand == 0) {
                    rand = (int) (Math.random() * 7);
                }
                Shape temp = shapesList.getFirst();
                shapesList.set(0, shapesList.get(rand));
                shapesList.set(rand, temp);
            }
        }

        Shape temp = shapesList.getFirst();
        shapesList.removeFirst();
        return temp;
//        return new LineShape();
    }

    public static Shape updateHeldShape(Shape currentShape) {
        if(wasHeldUsed)
            return currentShape;

        Shape returnValue;
        if(heldShape == null)
            returnValue = getRandomizedShape(currentShape);
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

    public static int getBlockSizePx() {
        return 50;
    }
}
