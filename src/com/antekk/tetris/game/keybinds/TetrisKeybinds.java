package com.antekk.tetris.game.keybinds;

import com.antekk.tetris.game.Shapes;
import com.antekk.tetris.view.TetrisGamePanel;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static com.antekk.tetris.game.Shapes.getCurrentShape;

public class TetrisKeybinds {
    private static boolean lockPauseKey = false;

    public static void setupKeyBindings(InputMap inputMap, ActionMap actionMap, TetrisGamePanel gamePanel) {
        if (gamePanel == null || inputMap == null || actionMap == null) {
            throw new RuntimeException("a value in keybinds is null");
        }

        TetrisKeybind.currentPanel = gamePanel;
        TetrisKeybind.inputMap = inputMap;
        TetrisKeybind.actionMap = actionMap;

        new TetrisKeybind("MOVE_RIGHT", KeyEvent.VK_RIGHT,
                () -> getCurrentShape().moveRight()
        ).bindKeyPressed();

        new TetrisKeybind("MOVE_LEFT", KeyEvent.VK_LEFT,
                () -> getCurrentShape().moveLeft()
        ).bindKeyPressed();

        new TetrisKeybind("MOVE_DOWN", KeyEvent.VK_DOWN,
                () -> getCurrentShape().moveDown()
        ).bindKeyPressed();

        new TetrisKeybind("HARD_DROP", KeyEvent.VK_SPACE,
                () -> getCurrentShape().hardDrop()
        ).bindKeyPressed();

        new TetrisKeybind("ROTATE_RIGHT", KeyEvent.VK_UP,
                () -> getCurrentShape().rotateRight()
        ).bindKeyPressed();

        new TetrisKeybind("ROTATE_LEFT", KeyEvent.VK_Z,
                () -> getCurrentShape().rotateLeft()
        ).bindKeyPressed();

        new TetrisKeybind("HELD_SHAPE", KeyEvent.VK_C,
                Shapes::swapHeldAndCurrentShapes
        ).bindKeyPressed();

        new TetrisKeybind("PAUSE_GAME_PRESSED", KeyEvent.VK_ESCAPE,
            () -> gamePanel.getGameLoop().pauseAndUnpauseGame()
        ).bindKeyPressed();
    }
}

