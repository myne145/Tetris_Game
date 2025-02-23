package com.antekk.tetris.view;

import com.antekk.tetris.game.Shapes;
import com.antekk.tetris.game.player.TetrisPlayer;
import com.antekk.tetris.view.themes.TetrisColors;
import com.antekk.tetris.view.themes.Theme;

import javax.swing.*;
import java.awt.*;

public class OptionsDialog extends JDialog {

    protected OptionsDialog(TetrisGamePanel parent) {
        super(SwingUtilities.getWindowAncestor(parent));
        setTitle("Options");
        setPreferredSize(new Dimension(TetrisGamePanel.getBoardCols() * Shapes.getBlockSizePx() * 2, (int) (0.8 * TetrisGamePanel.getBoardRows() * Shapes.getBlockSizePx())));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel generalOptions = new JPanel();
        BoxLayout layout = new BoxLayout(generalOptions, BoxLayout.Y_AXIS);
        generalOptions.setLayout(layout);

        DefaultComboBoxModel<Theme> model = new DefaultComboBoxModel<>();
        JComboBox<Theme> themeSelection = new JComboBox<>(model);
        for(Theme theme : Theme.values())
            model.addElement(theme);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1,1,30,1);
        JSpinner level = new JSpinner(spinnerModel);
        level.setPreferredSize(new Dimension(80,25));
        level.addChangeListener(e -> TetrisPlayer.defaultGameLevel = (int) spinnerModel.getValue() - 1);

        JPanel setGameLevel = new JPanel();
        setGameLevel.add(new JLabel("Set starting game level: "));
        setGameLevel.add(level);

        generalOptions.add(setGameLevel);

        JPanel theme = new JPanel();
        theme.add(new JLabel("Theme: "));
        theme.add(themeSelection);
        themeSelection.addItemListener(e -> {
            TetrisColors.setTheme((Theme) e.getItem());
            parent.setBackground(TetrisColors.backgroundColor);
            parent.repaint();
        });

        generalOptions.add(theme);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> this.dispose());

        tabbedPane.addTab("General", generalOptions);
        add(tabbedPane, BorderLayout.PAGE_START);
        add(okButton, BorderLayout.PAGE_END);

        pack();
    }
}
