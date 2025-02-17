package com.antekk.tetris.game.keybinds;

import com.antekk.tetris.view.TetrisGamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

record TetrisKeybind(String name, int keyCode, Runnable action) {
    static TetrisGamePanel currentPanel = null;
    static InputMap inputMap;
    static ActionMap actionMap;

    protected void bindKey() {
        TetrisKeybind.inputMap.put(KeyStroke.getKeyStroke(keyCode(), 0), name());
        TetrisKeybind.actionMap.put(name(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action().run();
                currentPanel.paintImmediately(0, 0, currentPanel.getWidth(), currentPanel.getHeight());
            }
        });
    }
}
