package com.antekk.tetris.game.loop;

import com.antekk.tetris.game.Shapes;
import com.antekk.tetris.view.TetrisGamePanel;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.antekk.tetris.game.Shapes.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.awt.GLData.API.GL;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameLoop extends Thread {
    private final TetrisGamePanel currentPanel;
    private float linesToMoveBlock = 0;
    private GameState gameState;
    long window;

    private final int timeBetweenFramesMillis = 1000 / 60;
    private int lastFrame = (int) System.currentTimeMillis();
    private int targetTime = lastFrame + timeBetweenFramesMillis;
    private int timeSinceInputProcessing = 0;



    private void gameLoop()  {
        while (gameState != GameState.LOST) {
            int currentTime = (int) System.currentTimeMillis();
            if(currentTime < targetTime || gameState == GameState.PAUSED)
                continue;

            glfwPollEvents();
            timeSinceInputProcessing++;
            if(timeSinceInputProcessing >= 6) {


                if(glfwGetKey(window, GLFW_KEY_RIGHT) == 1)
                    getCurrentShape().moveRight();

                if(glfwGetKey(window, GLFW_KEY_LEFT) == 1)
                    getCurrentShape().moveLeft();

                if(glfwGetKey(window, GLFW_KEY_DOWN) == 1)
                    getCurrentShape().moveDown();

                if(glfwGetKey(window, GLFW_KEY_SPACE) == 1)
                    getCurrentShape().hardDrop();

                if(glfwGetKey(window, GLFW_KEY_UP) == 1)
                    getCurrentShape().rotateRight();

                if(glfwGetKey(window, GLFW_KEY_Z) == 1)
                    getCurrentShape().rotateLeft();

                if(glfwGetKey(window, GLFW_KEY_C) == 1)
                    swapHeldAndCurrentShapes();



                timeSinceInputProcessing = 0;
            }

//            case KeyEvent.VK_RIGHT -> getCurrentShape().moveRight();
//            case KeyEvent.VK_LEFT -> getCurrentShape().moveLeft();
//            case KeyEvent.VK_DOWN -> getCurrentShape().moveDown();
//            case KeyEvent.VK_SPACE -> getCurrentShape().hardDrop();
//            case KeyEvent.VK_UP -> getCurrentShape().rotateRight();
//            case KeyEvent.VK_Z -> getCurrentShape().rotateLeft();
//            case KeyEvent.VK_C -> swapHeldAndCurrentShapes();


            linesToMoveBlock += Shapes.getSpeedBlocksPerSeconds();

            boolean canMoveDown = true;
            while (linesToMoveBlock >= 1) {
                canMoveDown = Shapes.getCurrentShape().moveDown();
                linesToMoveBlock--;
            }

            if(!canMoveDown) {
                getStationaryShapes().add(getCurrentShape());
                clearFullLines();
                updateCurrentShape();
                unlockHeld();
            }


            currentPanel.paintImmediately(0, 0, currentPanel.getWidth(),currentPanel.getHeight());

            gameState = getGameState();

            lastFrame = (int) System.currentTimeMillis();
            targetTime = lastFrame + timeBetweenFramesMillis;
        }

        Shapes.getCurrentPlayer().name = JOptionPane.showInputDialog(null, "Enter your name", "Save score", JOptionPane.INFORMATION_MESSAGE);
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
        /* Set the error callback */
        glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));

        /* Initialize GLFW */
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        /* Create window */
        window = glfwCreateWindow(640, 480, "Simple example", NULL, NULL);
        if (window == NULL) {
            glfwTerminate();
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwMakeContextCurrent(window);

        gameState = GameState.RUNNING;
        gameLoop();
    }

    public GameLoop(TetrisGamePanel panel) {
        this.currentPanel = panel;
    }
}
