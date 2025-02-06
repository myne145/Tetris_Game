package com.antekk.tetris.gameview;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Tetris");
        GamePanel panel = new GamePanel();
        this.add(panel);

        Dimension prefferedSize = panel.getPreferredSize();
        prefferedSize.height += 100;
        this.setPreferredSize(prefferedSize);

        this.pack();
        this.setVisible(true);
    }
}
