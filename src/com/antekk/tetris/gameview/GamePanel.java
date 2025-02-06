package com.antekk.tetris.gameview;

import com.antekk.tetris.blocks.Block;
import com.antekk.tetris.blocks.Shape;
import com.antekk.tetris.blocks.shapes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener {
    private final int RIGHT = getBoardCols() * Block.getSizePx();
    private final int BOTTOM = getBoardRows() * Block.getSizePx();
    private Shape currentShape;

    private void setRandomizedCurrentShape() {
        int rand = (int) (Math.random() * 7);
        switch(rand) {
            case 0 -> currentShape = new TShape();
            case 1 -> currentShape = new ZShape();
            case 2 -> currentShape = new JShape();
            case 3 -> currentShape = new LShape();
            case 4 -> currentShape = new LineShape();
            case 5 -> currentShape = new SquareShape();
            case 6 -> currentShape = new SShape();
        }
    }

    private void gameLoop() {


        //if the shape is already in the bottom
        if(!currentShape.moveDown()) {
            Shape.getStationaryShapes().add(currentShape);
            setRandomizedCurrentShape();
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
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);

        setRandomizedCurrentShape();
        Thread gameThread = getGameThread();
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Bottom
        g.setColor(Color.CYAN);
        g.fillRect(0, BOTTOM, RIGHT, 20);

        //Shapes
        currentShape.draw(g);
        for(Shape shape : Shape.getStationaryShapes()) {
            shape.draw(g);
        }
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

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT -> currentShape.moveRight();
            case KeyEvent.VK_LEFT -> currentShape.moveLeft();
            case KeyEvent.VK_DOWN -> currentShape.moveDown();
            case KeyEvent.VK_SPACE -> currentShape.hardDrop();
            default -> {
                return;
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
