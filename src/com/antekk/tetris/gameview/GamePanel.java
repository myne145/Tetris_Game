package com.antekk.tetris.gameview;

import com.antekk.tetris.blocks.Block;
import com.antekk.tetris.blocks.Shape;
import com.antekk.tetris.blocks.TestShape;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private final int RIGHT = getBoardCols() * Block.getSizePx();
    private final int BOTTOM = getBoardRows() * Block.getSizePx();
    private final ArrayList<Shape> stationaryShapes = new ArrayList<>();


    TestShape testShape = new TestShape();

    private void gameLoop() {


        //if the shape is already in the bottom
        if(!testShape.moveDown()) {
            stationaryShapes.add(testShape);
            testShape = new TestShape();
        }

        repaint();
        try {
            Thread.sleep((long) (1 / Shape.getSpeedBlocksPerSeconds() * 1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        gameLoop();
    }

    private Thread getGameThread() {
        return new Thread(this::gameLoop);
    }

    protected GamePanel() {
        Thread gameThread = getGameThread();
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        testShape.draw(g);
        for(Shape shape : stationaryShapes) {
            shape.draw(g);
        }

        g.setColor(Color.CYAN);
        g.fillRect(0, BOTTOM, RIGHT, 20);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(RIGHT, BOTTOM);
    }

    public static int getBoardRows() {
        return 20;
    }

    public static int getBoardCols() {
        return 10;
    }
}
