package com.antekk.tetris.view;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Tetris");
        TetrisGamePanel panel = new TetrisGamePanel();
        this.add(panel);

        Dimension prefferedSize = panel.getPreferredSize();
        prefferedSize.height += 100;
        prefferedSize.width += 20;
        this.setPreferredSize(prefferedSize);

        this.pack();
        this.setVisible(true);
    }
}
