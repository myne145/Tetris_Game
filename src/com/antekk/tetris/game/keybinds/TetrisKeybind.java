package com.antekk.tetris.game.keybinds;

import com.antekk.tetris.view.TetrisGamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

record TetrisKeybind(String name, int keyCode, Runnable action) {
    static TetrisGamePanel currentPanel = null;
    static InputMap inputMap;
    static ActionMap actionMap;

    void bindKeyPressed() {
        bind(0, false);
    }

    void bindKeyReleased() {
        bind(0, true);
    }


    private void bind(int modifiers, boolean onKeyRelease) {
        TetrisKeybind.inputMap.put(KeyStroke.getKeyStroke(keyCode(), modifiers, onKeyRelease), name());
        TetrisKeybind.actionMap.put(name(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action().run();
                currentPanel.paintImmediately(0, TetrisGamePanel.TOP, currentPanel.getWidth(), currentPanel.getHeight());
            }
        });
    }
}
