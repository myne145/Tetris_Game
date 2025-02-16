package com.antekk.tetris;

import com.antekk.tetris.view.GameWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}