package com.antekk.tetris.view;

import com.antekk.tetris.game.Shapes;
import com.antekk.tetris.game.loop.GameLoop;
import com.antekk.tetris.view.displays.score.LinesClearedDisplay;
import com.antekk.tetris.view.displays.score.ScoreDisplay;
import com.antekk.tetris.view.displays.shapes.HeldShapeDisplay;
import com.antekk.tetris.view.displays.shapes.NextShapeDisplay;
import com.antekk.tetris.game.shapes.Shape;

import javax.swing.*;
import java.awt.*;

import static com.antekk.tetris.game.Shapes.*;
import static com.antekk.tetris.game.Shapes.getShadow;
import static com.antekk.tetris.game.keybinds.TetrisKeybinds.setupKeyBindings;

public class TetrisGamePanel extends JPanel {
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

    public void repaintCurrentShape() {
        paintImmediately(LEFT + (getCurrentShape().getCenterPoint().x - 2) * Shapes.getBlockSizePx() - 1,
                TOP + (getCurrentShape().getCenterPoint().y - 2) * Shapes.getBlockSizePx() - 1,
                4 * Shapes.getBlockSizePx() + 2, 4 * Shapes.getBlockSizePx() + 2);
    }

    protected TetrisGamePanel() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        setupKeyBindings(inputMap, actionMap, this);

        setBackground(TetrisColors.backgroundColor);
        updateCurrentShape();
        GameLoop loop = new GameLoop(this);
        loop.start();
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
