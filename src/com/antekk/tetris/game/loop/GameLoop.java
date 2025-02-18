package com.antekk.tetris.game.loop;

import com.antekk.tetris.game.Shapes;
import com.antekk.tetris.game.player.TetrisPlayer;
import com.antekk.tetris.view.TetrisGamePanel;

import javax.swing.*;
import java.awt.*;

import static com.antekk.tetris.game.Shapes.*;

public class GameLoop extends Thread {
    private final TetrisGamePanel currentPanel;
    private float linesToMoveBlock = 0;
    private GameState gameState;
    private int framesSinceTetrominoLanded = 0;

    private final int timeBetweenFramesMillis = 1000 / 60;
    private int lastFrame = (int) System.currentTimeMillis();
    private int targetTime = lastFrame + timeBetweenFramesMillis;

    private void gameLoop()  {
        while (gameState != GameState.LOST) {
            int currentTime = (int) System.currentTimeMillis();
            if(currentTime < targetTime || gameState == GameState.PAUSED)
                continue;

            //30 frames of lock delay
            if(getCurrentShape().hasLanded) {
                framesSinceTetrominoLanded++;
            }

            linesToMoveBlock += Shapes.getFramesForBlockToMoveDown();

            boolean canMoveDown = true;
            while (linesToMoveBlock >= 1) {
                canMoveDown = Shapes.getCurrentShape().moveDown();
                linesToMoveBlock--;
                currentPanel.repaintCurrentShape();
            }

            if(!canMoveDown)
                getCurrentShape().hasLanded = true;

            if((getCurrentShape().hasLanded && framesSinceTetrominoLanded >= 30) ||
                    getCurrentShape().wasHardDropUsed()) {

                //so that the shape doesnt end up in the air (terrible solution imo, TODO: verify that i guess)
                while(Shapes.getCurrentShape().moveDown());

                framesSinceTetrominoLanded = 0;
                getCurrentShape().hasLanded = false;

                getStationaryShapes().add(getCurrentShape());
                clearFullLines();
                updateGameLevel();
                updateCurrentShape();
                unlockHeld();
                currentPanel.paintImmediately(0, 0, currentPanel.getWidth(), currentPanel.getHeight());
            }

            gameState = updateGameState();

            lastFrame = (int) System.currentTimeMillis();
            targetTime = lastFrame + timeBetweenFramesMillis;
        }
        getCurrentPlayer().name = JOptionPane.showInputDialog(
                null,
                "Enter your name",
                "Game over",
                JOptionPane.INFORMATION_MESSAGE
        );

        if(getCurrentPlayer().name != null)
            TetrisPlayer.getStatsFile().addPlayer(getCurrentPlayer());

    }

    private GameState updateGameState() {
        if(gameState == GameState.PAUSED) {
            return GameState.PAUSED;
        }

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

    public void pauseAndUnpauseGame() {
        if(gameState == GameState.LOST)
            return;

        if(gameState == GameState.PAUSED) {
            gameState = GameState.RUNNING;
        } else {
            gameState = GameState.PAUSED;
        }
    }

    @Override
    public void run() {
        gameState = GameState.RUNNING;
        gameLoop();
    }

    public GameLoop(TetrisGamePanel panel) {
        this.currentPanel = panel;
    }

    public GameState getGameState() {
        return gameState;
    }
}
