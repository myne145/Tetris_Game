package com.antekk.tetris.game;

import com.antekk.tetris.game.player.ScoreValue;
import com.antekk.tetris.game.player.TetrisPlayer;
import com.antekk.tetris.game.shapes.Shape;
import com.antekk.tetris.view.TetrisGamePanel;
import com.antekk.tetris.game.tetrominos.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Shapes {
    private static final ArrayList<Shape> shapesList = new ArrayList<>();
    private static final ArrayList<Shape> stationaryShapes = new ArrayList<>();
    private static Shape heldShape;
    private static Shape currentShape;
    private static boolean wasHeldUsed = false;
    private static Shape shadow;
    private static int comboCounter = -1;
    private static int targetLinesForNextLevel = 0;
    private static int currentLinesForNextLevel = 0;
    private static TetrisGamePanel gamePanel;
    private static TetrisPlayer currentPlayer;

    private static final ArrayList<Double> speedValues = new ArrayList<>(
           Arrays.asList(0.01667, 0.021017, 0.026977, 0.035256, 0.04693, 0.06361, 0.0879, 0.1236,
                    0.1775, 0.2598, 0.388, 0.59, 0.92, 1.46, 2.36, 3.91, 6.61, 11.43, 20.23, 36.6)
    );


    private static void fillShapeListWithAllShapeTypes() {
        shapesList.add(new TShape());
        shapesList.add(new ZShape());
        shapesList.add(new JShape());
        shapesList.add(new LShape());
        shapesList.add(new LineShape());
        shapesList.add(new SquareShape());
        shapesList.add(new SShape());
    }

    public static Shape getRandomizedShape() {
        if(shapesList.isEmpty()) {
            fillShapeListWithAllShapeTypes();
            shapesList.sort((o1, o2) -> (int) (Math.random() * 2) - 1);
        }


        if(shapesList.size() == 1) {
            //filling the array
            fillShapeListWithAllShapeTypes();

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

        currentShape.setAsHeldShape();
        heldShape = currentShape;

        returnValue.setDefaultValues();
        lockHeld();
        return returnValue;
    }

    public static void clearFullLines() {
        int start = 0;
        int end = Integer.MAX_VALUE;
        for(int currentYpos = 0; currentYpos < TetrisGamePanel.getBoardRows(); currentYpos++) {
            boolean isLineFull = isLineFull(currentYpos);
            if(isLineFull && currentYpos > start) {
                start = currentYpos;
            }
            if(isLineFull && currentYpos < end) {
                end = currentYpos;
            }
        }

        if(end == Integer.MAX_VALUE) {
            comboCounter = -1;
            return;
        }

        comboCounter++;
        clearLineAt(start, end);
        getCurrentPlayer().addScore(ScoreValue.COMBO.getValue() * comboCounter * getCurrentPlayer().level);
    }

    private static boolean isLineFull(int y) {
        ArrayList<Integer> xPositions = new ArrayList<>();

        for(Shape shape : getStationaryShapes()) {
            for(Point p : shape.getCollisionPoints()) {
                if(p.y == y)
                    xPositions.add(p.x);
            }
        }

        return xPositions.size() == TetrisGamePanel.getBoardCols();
    }

    //TODO: fix this logic
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

        int clearedLines = yEnd - yStart + 1;
        for(Shape shape : getStationaryShapes()) {
            for(Point p : shape.getCollisionPoints()) {
                if(p.y <= yStart) {
                    p.translate(0, clearedLines);
                }

            }
        }
        currentPlayer.linesCleared += clearedLines;
        currentLinesForNextLevel += clearedLines;
        currentPlayer.addScore(ScoreValue.fromClearedLines(clearedLines));
    }

    public static void updateGameLevel() {
        if(currentLinesForNextLevel < targetLinesForNextLevel)
            return;


        getCurrentPlayer().level++;
        targetLinesForNextLevel = getCurrentPlayer().level * 5;
        currentLinesForNextLevel = 0;
    }

    public static void startNewGame() {
        shapesList.clear();
        stationaryShapes.clear();
        heldShape = null;
        currentPlayer = new TetrisPlayer(gamePanel.getScoreBonusDisplay());
        targetLinesForNextLevel = 0;
        currentLinesForNextLevel = 0;
        wasHeldUsed = false;

        updateGameLevel();
        updateCurrentShape();
    }

    public static void swapHeldAndCurrentShapes() {
        currentShape = updateHeldShape(currentShape);
        shadow = (Shape) currentShape.clone();

        shadow.reloadShadow();
        while(shadow.moveVertically());
    }

    public static Shape getCurrentShape() {
        return currentShape;
    }

    public static void updateCurrentShape() {
        currentShape = getRandomizedShape();
        shadow = (Shape) currentShape.clone();

        shadow.reloadShadow();
        while(shadow.moveVertically());
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

    public static float getFramesForBlockToMoveDown() {
        return speedValues.get(Math.min(getCurrentPlayer().level - 1, speedValues.size())).floatValue();
    }

    public static TetrisPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public static long getLinesCleared() {
        return getCurrentPlayer().linesCleared;
    }

    public static Shape getShadow() {
        return shadow;
    }

    public static void setGamePanel(TetrisGamePanel gamePanel) {
        Shapes.gamePanel = gamePanel;
    }
}