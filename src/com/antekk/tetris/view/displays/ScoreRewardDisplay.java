package com.antekk.tetris.view.displays;

import com.antekk.tetris.game.Shapes;
import com.antekk.tetris.view.TetrisColors;
import com.antekk.tetris.view.TetrisGamePanel;

import javax.swing.*;
import java.awt.*;

public class ScoreRewardDisplay extends JPanel implements Runnable{
    private Thread animationThread;
    private int alpha = 255;
    private Color textColor = TetrisColors.foregroundColor;
    private final JLabel topText = new JLabel();
    private final JLabel bottomText = new JLabel();

    public ScoreRewardDisplay() {
        super();
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        setOpaque(false);

        topText.setFont(this.getFont().deriveFont((float) (0.8 * Shapes.getBlockSizePx())));
        bottomText.setFont(this.getFont().deriveFont((float) (0.6 * Shapes.getBlockSizePx())));

        add(Box.createRigidArea(new Dimension(10, (TetrisGamePanel.getBoardRows() / 2) * Shapes.getBlockSizePx())));
        add(topText);
        add(bottomText);
    }


    public void setText(String top, String bottom) {
        topText.setText(top);
        bottomText.setText(bottom);

        alpha = 255;
        animationThread = new Thread(this);
        animationThread.start();
    }

    @Override
    public void run() {
        topText.setForeground(textColor);
        bottomText.setForeground(textColor);
        this.setVisible(true);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while(alpha >= 0) {
            topText.setForeground(new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), alpha));
            bottomText.setForeground(new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), alpha));
            alpha -= 10;

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        this.setVisible(false);
    }


}