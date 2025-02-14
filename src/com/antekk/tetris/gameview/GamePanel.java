package com.antekk.tetris.gameview;

import com.antekk.tetris.gameview.displaypanels.HeldShapePanel;
import com.antekk.tetris.gameview.displaypanels.NextShapePanel;
import com.antekk.tetris.shapes.Shape;
import com.antekk.tetris.shapes.Shapes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.antekk.tetris.shapes.Shapes.*;
import static com.antekk.tetris.shapes.Shapes.getCurrentShape;
import static com.antekk.tetris.shapes.Shapes.updateCurrentShape;

public class GamePanel extends JPanel implements KeyListener {
    public static final int LEFT = 8 * getBlockSizePx();
    public static final int TOP = getBlockSizePx();
    public static final int RIGHT = getBoardCols() * getBlockSizePx();
    public static final int BOTTOM = getBoardRows() * getBlockSizePx();

    private final HeldShapePanel heldShapePanel = new HeldShapePanel();
    private final NextShapePanel nextShapePanel = new NextShapePanel();

    private void gameLoop() {
//        getCurrentShape().rotateRight();
        //if the shape is already in the bottom
        if(!getCurrentShape().moveDown()) {
            getStationaryShapes().add(getCurrentShape());
            Shapes.clearFullLines();
            updateCurrentShape();
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

    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Main game area
        g.setColor(TetrisColors.foregroundColor); //border
        g.drawRoundRect(LEFT, TOP, RIGHT, BOTTOM,8,8);
        g.setColor(TetrisColors.backgroundColor); //fill
        g.fillRoundRect(LEFT+1, TOP+1, RIGHT-1, BOTTOM-1,8,8);

        heldShapePanel.drawPanel(g);
        nextShapePanel.drawPanel(g);

        //Shapes
        getCurrentShape().draw(g);
        for(Shape shape : getStationaryShapes()) { //TODO ConcurrentModificationException here
            shape.draw(g);
        }
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
            case KeyEvent.VK_C -> swapHeldAndCurrentShapes();
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

    protected GamePanel() {
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);
        setBackground(TetrisColors.backgroundColor);

        Shapes.updateCurrentShape();
        Thread gameThread = getGameThread();
        gameThread.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(RIGHT + LEFT + 8 * Shapes.getBlockSizePx(), BOTTOM + TOP);
    }

    public static int getBoardRows() {
        return 20;
    }

    public static int getBoardCols() {
        return 10;
    }

    private Thread getGameThread() {
        return new Thread(this::gameLoop);
    }
}
