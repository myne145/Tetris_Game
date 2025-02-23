package com.antekk.tetris.view;

import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Tetris");
        TetrisGamePanel panel = new TetrisGamePanel(this);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }
}
