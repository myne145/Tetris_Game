package com.antekk.tetris.gameview;

import com.antekk.tetris.blocks.Shape;
import com.antekk.tetris.blocks.Block;
import com.antekk.tetris.blocks.Shapes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.antekk.tetris.blocks.Shapes.getStationaryShapes;
import static com.antekk.tetris.blocks.Shapes.unlockHeld;

public class GamePanel extends JPanel implements KeyListener {
    public static final int LEFT = 8 * Block.getSizePx();
    public static final int TOP = Block.getSizePx();
    public static final int RIGHT = getBoardCols() * Block.getSizePx();
    public static final int BOTTOM = getBoardRows() * Block.getSizePx();
    private Shape currentShape;


    private void gameLoop() {
        //if the shape is already in the bottom
        if(!currentShape.moveDown()) {
            getStationaryShapes().add(currentShape);
            currentShape = Shapes.getRandomizedShape(currentShape);
            unlockHeld();
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

        currentShape = Shapes.getRandomizedShape(currentShape);
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
        if(Shapes.getHeldShape() != null)
            Shapes.getHeldShape().drawHeldShape(g);


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
            case KeyEvent.VK_C -> currentShape = Shapes.updateHeldShape(currentShape);
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
