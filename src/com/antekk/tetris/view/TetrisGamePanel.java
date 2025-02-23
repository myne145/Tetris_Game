package com.antekk.tetris.view;

import com.antekk.tetris.game.ConfigJSON;
import com.antekk.tetris.game.Shapes;
import com.antekk.tetris.game.loop.GameLoop;
import com.antekk.tetris.game.loop.GameState;
import com.antekk.tetris.game.player.TetrisPlayer;
import com.antekk.tetris.view.displays.ScoreRewardDisplay;
import com.antekk.tetris.view.displays.score.LevelDisplay;
import com.antekk.tetris.view.displays.score.LinesClearedDisplay;
import com.antekk.tetris.view.displays.score.ScoreDisplay;
import com.antekk.tetris.view.displays.shapes.HeldShapeDisplay;
import com.antekk.tetris.view.displays.shapes.NextShapeDisplay;
import com.antekk.tetris.game.shapes.Shape;
import com.antekk.tetris.view.themes.TetrisColors;
import com.antekk.tetris.view.themes.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.antekk.tetris.game.Shapes.*;
import static com.antekk.tetris.game.Shapes.getGhostShape;
import static com.antekk.tetris.game.keybinds.TetrisKeybinds.setupKeyBindings;

public class TetrisGamePanel extends JPanel {
    public static int LEFT;
    public static int TOP;
    public static int RIGHT;
    public static int BOTTOM;

    private final HeldShapeDisplay heldShapeDisplay;
    private final NextShapeDisplay nextShapeDisplay;

    private final ScoreDisplay scorePanel;
    private final LinesClearedDisplay linesClearedDisplay;
    private final LevelDisplay levelDisplay;

    private final GameLoop loop = new GameLoop(this);
    private final BestPlayersDialog bestPlayersDialog = new BestPlayersDialog(this);
    private final ScoreRewardDisplay addedScoreText;
    private final JPanel toolbar = new JPanel();
    private final OptionsDialog optionsDialog;

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
        g1.setColor(TetrisColors.borderColor); //border
        g1.drawRoundRect(LEFT, TOP, RIGHT, BOTTOM,8,8);
        g1.setColor(TetrisColors.boardColor); //fill
        g1.fillRoundRect(LEFT+1, TOP+1, RIGHT-1, BOTTOM-1,8,8);

        heldShapeDisplay.drawDisplay(g1);
        nextShapeDisplay.drawDisplay(g1);
        scorePanel.drawDisplay(g1);
        linesClearedDisplay.drawDisplay(g1);
        levelDisplay.drawDisplay(g1);

        if(getGhostShape() != null)
            getGhostShape().drawGhostShape((Graphics2D) g1);

        //Shapes
        getCurrentShape().draw(g1);
        for(Shape shape : getStationaryShapes()) { //TODO ConcurrentModificationException here
            shape.draw(g1);
        }

//        for(int i = 0; i <= getBoardCols(); i++) {
//            g.drawLine(LEFT + i * Shapes.getBlockSizePx(), TOP, LEFT + i * Shapes.getBlockSizePx(), BOTTOM + Shapes.getBlockSizePx());
//        }
//
//        for(int i = 0; i <= getBoardRows(); i++) {
//            g.drawLine(LEFT, i * Shapes.getBlockSizePx() + TOP, RIGHT + LEFT, i * Shapes.getBlockSizePx() + TOP);
//        }
    }

    public void repaintBoard() {
        paintImmediately(getBlockSizePx(), TOP, getWidth(), getHeight());
    }

    private void drawPauseScreen(Graphics g) {
        g.setColor(TetrisColors.boardColor);
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

    protected TetrisGamePanel(JFrame parent) {
        ConfigJSON.setValuesFromConfig();
        Shapes.setGamePanel(this);

        LEFT = 8 * getBlockSizePx();
        TOP = getBlockSizePx();
        RIGHT = getBoardCols() * getBlockSizePx();
        BOTTOM = getBoardRows() * getBlockSizePx();
        parent.setPreferredSize(this.getPreferredSize());

        scorePanel = new ScoreDisplay();
        linesClearedDisplay = new LinesClearedDisplay();
        levelDisplay = new LevelDisplay();
        optionsDialog = new OptionsDialog(this);
        addedScoreText = new ScoreRewardDisplay();
        heldShapeDisplay = new HeldShapeDisplay();
        nextShapeDisplay = new NextShapeDisplay();

        setLayout(new BorderLayout());
        setDoubleBuffered(true);
        setBackground(TetrisColors.backgroundColor);

        JButton newGame = new JButton("New game");
        JButton pauseGame = new JButton("Pause game");
        JButton showBestPlayers = new JButton("Best players");
        JButton options = new JButton("Options");

        newGame.setPreferredSize(new Dimension(3 * Shapes.getBlockSizePx(), (int) (0.65 * getBlockSizePx())));
        pauseGame.setPreferredSize(new Dimension(3 * Shapes.getBlockSizePx(), (int) (0.65 * getBlockSizePx())));
        showBestPlayers.setPreferredSize(new Dimension(3 * Shapes.getBlockSizePx(), (int) (0.65 * getBlockSizePx())));
        options.setPreferredSize(new Dimension(3 * Shapes.getBlockSizePx(), (int) (0.65 * getBlockSizePx())));

        BoxLayout layout = new BoxLayout(toolbar, BoxLayout.X_AXIS);
        toolbar.setLayout(layout);
        toolbar.setBackground(TetrisColors.backgroundColor);

        toolbar.add(Box.createRigidArea(new Dimension(Shapes.getBlockSizePx(), 3)));
        toolbar.add(newGame);
        toolbar.add(Box.createRigidArea(new Dimension(Shapes.getBlockSizePx(), 3)));
        toolbar.add(pauseGame);
        toolbar.add(Box.createRigidArea(new Dimension(Shapes.getBlockSizePx(), 3)));
        toolbar.add(showBestPlayers);
        toolbar.add(Box.createRigidArea(new Dimension(Shapes.getBlockSizePx(), 3)));
        toolbar.add(options);

        add(toolbar, BorderLayout.PAGE_START);
        add(addedScoreText, BorderLayout.CENTER);

        newGame.setFocusable(false);
        pauseGame.setFocusable(false);
        showBestPlayers.setFocusable(false);
        options.setFocusable(false);

        newGame.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(null,
                    "Do you really want to start a new game at level " + (TetrisPlayer.defaultGameLevel + 1) + "?",
                    "Are you sure?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if(option == 1)
                return;

            Shapes.startNewGame();
        });

        pauseGame.addActionListener(e -> {
            loop.pauseAndUnpauseGame();
            repaint();
        });

        options.addActionListener(e -> optionsDialog.setVisible(true));

        addedScoreText.setBorder(new EmptyBorder(new Insets(0,LEFT + RIGHT + Shapes.getBlockSizePx(), 0, 0)));
        showBestPlayers.addActionListener(e -> showBestPlayersDialog(!bestPlayersDialog.isVisible()));

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        setupKeyBindings(inputMap, actionMap, this);

        Shapes.startNewGame();

        loop.start();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(RIGHT + LEFT + 8 * getBlockSizePx() + Shapes.getBlockSizePx() / 2, (int) (BOTTOM + TOP + 2.5 * getBlockSizePx()));
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if(toolbar != null)
            toolbar.setBackground(bg);
    }

    public static int getBoardRows() {
        return 20;
    }

    public static int getBoardCols() {
        return 10;
    }

    public GameLoop getGameLoop() {
        return loop;
    }

    public ScoreRewardDisplay getScoreBonusDisplay() {
        return addedScoreText;
    }

    public void showBestPlayersDialog(boolean show) {
        if(!show) {
            bestPlayersDialog.dispose();
            return;
        }

        if(getGameLoop().getGameState() == GameState.RUNNING) {
            getGameLoop().pauseAndUnpauseGame();
        }
        repaint();

        bestPlayersDialog.reloadData();
        bestPlayersDialog.setVisible(true);
    }
}
