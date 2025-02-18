package com.antekk.tetris.view;

import com.antekk.tetris.game.Shapes;
import com.antekk.tetris.game.loop.GameLoop;
import com.antekk.tetris.game.loop.GameState;
import com.antekk.tetris.view.displays.score.LevelDisplay;
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
    private final LevelDisplay levelDisplay = new LevelDisplay();

    private final GameLoop loop = new GameLoop(this);

    @Override
    protected synchronized void paintComponent(Graphics g1) {
        super.paintComponent(g1);

        Graphics2D g = (Graphics2D)  g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);



        if(loop.getGameState() == GameState.PAUSED) {
            drawPauseScreen(g1);
            return;
        }

        //Main game area
        g1.setColor(TetrisColors.foregroundColor); //border
        g1.drawRoundRect(LEFT, TOP, RIGHT, BOTTOM,8,8);
        g1.setColor(TetrisColors.backgroundColor); //fill
        g1.fillRoundRect(LEFT+1, TOP+1, RIGHT-1, BOTTOM-1,8,8);

        heldShapeDisplay.drawDisplay(g1);
        nextShapeDisplay.drawDisplay(g1);
        scorePanel.drawDisplay(g1);
        linesClearedDisplay.drawDisplay(g1);
        levelDisplay.drawDisplay(g1);

        if(getShadow() != null)
            getShadow().drawAsShadow((Graphics2D) g1);

        //Shapes
        getCurrentShape().draw(g1);
        for(Shape shape : getStationaryShapes()) { //TODO ConcurrentModificationException here
            shape.draw(g1);
        }
    }

    public void repaintCurrentShape() {
        paintImmediately(LEFT + (getCurrentShape().getCenterPoint().x - 2) * Shapes.getBlockSizePx() - 1,
                TOP + (getCurrentShape().getCenterPoint().y - 2) * Shapes.getBlockSizePx() - 1,
                4 * Shapes.getBlockSizePx() + 2, 4 * Shapes.getBlockSizePx() + 2);
    }

    private void drawPauseScreen(Graphics g) {
        g.setColor(TetrisColors.backgroundColor);
        g.fillRect(0,0,getWidth(),getHeight());

        g.setColor(TetrisColors.foregroundColor);
        g.setFont(g.getFont().deriveFont((float) Shapes.getBlockSizePx()));
        g.drawString("Game paused", getBoardCols() / 2 * Shapes.getBlockSizePx(),
                getBoardRows() / 4 * Shapes.getBlockSizePx());

        g.drawString("Score: " + getCurrentPlayer().score, getBoardCols() / 2 * Shapes.getBlockSizePx(),
                (int) ((getBoardRows() / 4 + 5.5) * Shapes.getBlockSizePx()));

        g.drawString("Lines cleared: " + getCurrentPlayer().linesCleared, getBoardCols() / 2 * Shapes.getBlockSizePx(),
                (int) ((getBoardRows() / 4 + 6.8) * Shapes.getBlockSizePx()));

        g.drawString("Level: " + getCurrentPlayer().level, getBoardCols() / 2 * Shapes.getBlockSizePx(),
                (int) ((getBoardRows() / 4 + 8.1) * Shapes.getBlockSizePx()));

    }

    protected TetrisGamePanel() {
        setLayout(new BorderLayout());
        JButton newGame = new JButton("New game");
        JButton pauseGame = new JButton("Pause game");

        JPanel toolbar = new JPanel();
        BoxLayout layout = new BoxLayout(toolbar, BoxLayout.X_AXIS);
        toolbar.setLayout(layout);

        toolbar.add(newGame);
        toolbar.add(Box.createRigidArea(new Dimension(Shapes.getBlockSizePx(), 3)));
        toolbar.add(pauseGame);


        add(toolbar, BorderLayout.PAGE_START);

        newGame.setFocusable(false);
        pauseGame.setFocusable(false);

        pauseGame.addActionListener(e -> {
            loop.pauseAndUnpauseGame();
            repaint();
        });


        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        setupKeyBindings(inputMap, actionMap, this);

        updateGameLevel();
        updateCurrentShape();

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

    public static int getBlockSizePx() {
        return 30;
    }

    public GameLoop getGameLoop() {
        return loop;
    }
}
