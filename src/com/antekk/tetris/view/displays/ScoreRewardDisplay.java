package com.antekk.tetris.view.displays;

import com.antekk.tetris.game.Shapes;
import com.antekk.tetris.view.themes.TetrisColors;
import com.antekk.tetris.view.TetrisGamePanel;

import javax.swing.*;
import java.awt.*;

public class ScoreRewardDisplay extends JPanel implements Runnable{
    private Thread animationThread;
    private int alpha = 255;
    private final JLabel topText = new JLabel();
    private final JLabel bottomText = new JLabel();
    private boolean isDisplaying = false;

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
        if(isDisplaying)
            return;

        topText.setText(top);
        bottomText.setText(bottom);

        alpha = 255;
        animationThread = new Thread(this);
        animationThread.start();
    }

    @Override
    public void run() {
        isDisplaying = true;
        topText.setForeground(TetrisColors.foregroundColor);
        bottomText.setForeground(TetrisColors.foregroundColor);
        this.setVisible(true);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            isDisplaying = false;
            throw new RuntimeException(e);
        }
        while(alpha >= 0) {
            topText.setForeground(new Color(TetrisColors.foregroundColor.getRed(),
                    TetrisColors.foregroundColor.getGreen(),
                    TetrisColors.foregroundColor.getBlue(),
                    alpha)
            );
            bottomText.setForeground(new Color(TetrisColors.foregroundColor.getRed(),
                    TetrisColors.foregroundColor.getGreen(),
                    TetrisColors.foregroundColor.getBlue(),
                    alpha)
            );
            alpha -= 10;

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                isDisplaying = false;
                throw new RuntimeException(e);
            }

        }
        isDisplaying = false;
        this.setVisible(false);
    }
}