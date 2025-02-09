package com.antekk.tetris.gameview;

import com.antekk.tetris.blocks.Shape;
import com.antekk.tetris.blocks.Block;
import com.antekk.tetris.blocks.shapes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static com.antekk.tetris.blocks.Shape.getStationaryShapes;

public class GamePanel extends JPanel implements KeyListener {
    public static final int LEFT = 8 * Block.getSizePx();
    public static final int TOP = Block.getSizePx();
    private final int RIGHT = getBoardCols() * Block.getSizePx();
    private final int BOTTOM = getBoardRows() * Block.getSizePx();
    private Shape currentShape;
    private final ArrayList<Shape> shapesList = new ArrayList<>();
    private Shape heldShape = new LineShape();

    private void setRandomizedCurrentShape() {
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
            if(currentShape != null && shapesList.getFirst().getClass() == currentShape.getClass()) {
                int rand = 0;
                while(rand == 0) {
                    rand = (int) (Math.random() * 7);
                }
                Shape temp = shapesList.getFirst();
                shapesList.set(0, shapesList.get(rand));
                shapesList.set(rand, temp);
            }
        }
        currentShape = shapesList.getFirst();
        shapesList.removeFirst();
    }

    private void gameLoop() {
        //if the shape is already in the bottom
        if(!currentShape.moveDown()) {
            getStationaryShapes().add(currentShape);
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
        setBackground(new Color(238, 240, 242));

        setRandomizedCurrentShape();
        Thread gameThread = getGameThread();
        gameThread.start();
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Main game area
        g.setColor(new Color(28, 28, 28)); //border
        g.drawRoundRect(LEFT, TOP, RIGHT, BOTTOM,8,8);
        g.setColor(new Color(250, 250, 255)); //fill
        g.fillRoundRect(LEFT+1, TOP+1, RIGHT-1, BOTTOM-1,8,8);

        //hold
        g.fillRoundRect(Block.getSizePx() + 1, TOP+1, 6 * Block.getSizePx() - 1, 6 * Block.getSizePx() - 1,8,8);
        g.setColor(new Color(28, 28, 28)); //border
        g.setFont(g.getFont().deriveFont(36f).deriveFont(Font.BOLD));
        g.drawString("HELD", 3 * Block.getSizePx() - 5, TOP + Block.getSizePx());
        g.drawRoundRect(Block.getSizePx(), TOP, 6 * Block.getSizePx(), 6 * Block.getSizePx(),8,8);
        //TODO: temp
        for(Point p : heldShape.getCollisionPoints()) {
            //Fill
            g.setColor(heldShape.getColor());
            g.fillRect(Block.getSizePx() * p.x - Block.getSizePx(), GamePanel.TOP + p.y * Block.getSizePx() + Block.getSizePx(), Block.getSizePx(), Block.getSizePx());

            //Border
            g.setColor(Color.BLACK);
            g.drawRect(Block.getSizePx() * p.x - Block.getSizePx(), GamePanel.TOP + p.y * Block.getSizePx() + Block.getSizePx(), Block.getSizePx(), Block.getSizePx());
        }


        //Shapes
        currentShape.draw(g);
        for(Shape shape : getStationaryShapes()) {
            shape.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(RIGHT + LEFT + 200, BOTTOM + TOP);
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
            case KeyEvent.VK_UP -> currentShape.rotateRight();
            case KeyEvent.VK_Z -> currentShape.rotateLeft();
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
