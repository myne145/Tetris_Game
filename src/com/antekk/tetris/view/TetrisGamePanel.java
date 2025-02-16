package com.antekk.tetris.view;

import com.antekk.tetris.game.loop.GameLoop;
import com.antekk.tetris.view.displays.score.LinesClearedDisplay;
import com.antekk.tetris.view.displays.score.ScoreDisplay;
import com.antekk.tetris.view.displays.shapes.HeldShapeDisplay;
import com.antekk.tetris.view.displays.shapes.NextShapeDisplay;
import com.antekk.tetris.game.shapes.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.antekk.tetris.game.Shapes.*;
import static com.antekk.tetris.game.Shapes.getShadow;

public class TetrisGamePanel extends JPanel implements KeyListener {
    public static final int LEFT = 8 * getBlockSizePx();
    public static final int TOP = getBlockSizePx();
    public static final int RIGHT = getBoardCols() * getBlockSizePx();
    public static final int BOTTOM = getBoardRows() * getBlockSizePx();

    private final HeldShapeDisplay heldShapeDisplay = new HeldShapeDisplay();
    private final NextShapeDisplay nextShapeDisplay = new NextShapeDisplay();

    private final ScoreDisplay scorePanel = new ScoreDisplay();
    private final LinesClearedDisplay linesClearedDisplay = new LinesClearedDisplay();

    public static int getBlockSizePx() {
        return 40;
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Main game area
        g.setColor(TetrisColors.foregroundColor); //border
        g.drawRoundRect(LEFT, TOP, RIGHT, BOTTOM,8,8);
        g.setColor(TetrisColors.backgroundColor); //fill
        g.fillRoundRect(LEFT+1, TOP+1, RIGHT-1, BOTTOM-1,8,8);

        heldShapeDisplay.drawDisplay(g);
        nextShapeDisplay.drawDisplay(g);
        scorePanel.drawDisplay(g);
        linesClearedDisplay.drawDisplay(g);

        if(getShadow() != null)
            getShadow().drawAsShadow((Graphics2D) g);

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

    protected TetrisGamePanel() {
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);
        setBackground(TetrisColors.backgroundColor);

        updateCurrentShape();
        GameLoop loop = new GameLoop(this);
        loop.start();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(RIGHT + LEFT + 8 * getBlockSizePx(), BOTTOM + TOP);
    }

    public static int getBoardRows() {
        return 20;
    }

    public static int getBoardCols() {
        return 10;
    }
}
