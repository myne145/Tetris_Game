package com.antekk.tetris.game.loop;

import com.antekk.tetris.game.Shapes;
import com.antekk.tetris.view.TetrisGamePanel;

import java.awt.*;

import static com.antekk.tetris.game.Shapes.*;

public class GameLoop extends Thread {
    private final TetrisGamePanel currentPanel;
    private float linesToMoveBlock = 0;
    private GameState gameState;

    private final int timeBetweenFramesMillis = 1000 / 60;
    private int lastFrame = (int) System.currentTimeMillis();
    private int targetTime = lastFrame + timeBetweenFramesMillis;

    private void gameLoop()  {
        while (gameState != GameState.LOST) {
            int currentTime = (int) System.currentTimeMillis();
            if(currentTime < targetTime || gameState == GameState.PAUSED)
                continue;


            linesToMoveBlock += Shapes.getSpeedBlocksPerSeconds();

            boolean canMoveDown = true;
            while (linesToMoveBlock >= 1) {
                canMoveDown = Shapes.getCurrentShape().moveDown();
                linesToMoveBlock--;
                currentPanel.repaintCurrentShape();
            }

            if(!canMoveDown) {
                getStationaryShapes().add(getCurrentShape());
                clearFullLines();
                updateCurrentShape();
                unlockHeld();
                currentPanel.paintImmediately(0, 0, currentPanel.getWidth(), currentPanel.getHeight());
            }

            gameState = getGameState();

            lastFrame = (int) System.currentTimeMillis();
            targetTime = lastFrame + timeBetweenFramesMillis;
        }
    }

    private static GameState getGameState() { //TODO
        if(getCurrentShape() == null)
            return GameState.RUNNING;

        if(!getCurrentShape().getDefaultCollisionPoints().equals(getCurrentShape().getCollisionPoints())) {
            return GameState.RUNNING;
        }

        for(Point p : getCurrentShape().getCollisionPoints()) {
            if(!getCurrentShape().getCollisionsForPoint(p).isEmpty())
                return GameState.LOST;
        }
        return GameState.RUNNING;
    }

    @Override
    public void run() {
        gameState = GameState.RUNNING;
        gameLoop();
    }

    public GameLoop(TetrisGamePanel panel) {
        this.currentPanel = panel;
    }
}
