package com.antekk.tetris.gameview;

import com.antekk.tetris.blocks.Shape;
import com.antekk.tetris.blocks.Shapes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.antekk.tetris.blocks.Shapes.*;
import static com.antekk.tetris.blocks.Shapes.getCurrentShape;
import static com.antekk.tetris.blocks.Shapes.setCurrentShape;

public class GamePanel extends JPanel implements KeyListener {
    public static final int LEFT = 8 * getBlockSizePx();
    public static final int TOP = getBlockSizePx();
    public static final int RIGHT = getBoardCols() * getBlockSizePx();
    public static final int BOTTOM = getBoardRows() * getBlockSizePx();


    private void gameLoop() {
        //if the shape is already in the bottom
        if(!getCurrentShape().moveDown()) {
            getStationaryShapes().add(getCurrentShape());
            setCurrentShape(Shapes.getRandomizedShape(getCurrentShape()));
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

        Shapes.setCurrentShape(getRandomizedShape(getCurrentShape()));
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
        g.fillRoundRect(getBlockSizePx() + 1, TOP+1, 6 * getBlockSizePx() - 1, 6 * getBlockSizePx() - 1,8,8);
        g.setColor(new Color(28, 28, 28)); //border
        g.setFont(g.getFont().deriveFont(36f).deriveFont(Font.BOLD));
        g.drawString("HELD", 3 * getBlockSizePx() - 5, TOP + getBlockSizePx());
        g.drawRoundRect(getBlockSizePx(), TOP, 6 * getBlockSizePx(), 6 * getBlockSizePx(),8,8);
        //TODO: temp
        if(Shapes.getHeldShape() != null)
            Shapes.getHeldShape().drawHeldShape(g);

        //Shapes
        getCurrentShape().draw(g);
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
            case KeyEvent.VK_RIGHT -> getCurrentShape().moveRight();
            case KeyEvent.VK_LEFT -> getCurrentShape().moveLeft();
            case KeyEvent.VK_DOWN -> getCurrentShape().moveDown();
            case KeyEvent.VK_SPACE -> getCurrentShape().hardDrop();
            case KeyEvent.VK_UP -> getCurrentShape().rotateRight();
            case KeyEvent.VK_Z -> getCurrentShape().rotateLeft();
            case KeyEvent.VK_C -> setCurrentShape(updateHeldShape(getCurrentShape()));
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
