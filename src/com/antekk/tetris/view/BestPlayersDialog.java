package com.antekk.tetris.view;

import com.antekk.tetris.game.Shapes;
import com.antekk.tetris.game.player.TetrisPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BestPlayersDialog extends JDialog {
    private final DefaultTableModel model = new DefaultTableModel();
    private final JTable playerList = new JTable(model);
    private final JScrollPane scrollPane = new JScrollPane(playerList);

    protected void reloadData() {
        model.setRowCount(0);
        for(TetrisPlayer player : TetrisPlayer.playerStats.getPlayers()) {
            model.addRow(new String[] {
                    player.name, String.valueOf(player.score),
                    String.valueOf(player.linesCleared), String.valueOf(player.level)
            });
        }
    }

    protected BestPlayersDialog(TetrisGamePanel parent) {
        super(SwingUtilities.getWindowAncestor(parent));

        setTitle("Best players");
        setPreferredSize(new Dimension(TetrisGamePanel.getBoardCols() * Shapes.getBlockSizePx() * 2, (int) (0.8 * TetrisGamePanel.getBoardRows() * Shapes.getBlockSizePx())));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        model.addColumn("Name");
        model.addColumn("Score");
        model.addColumn("Lines cleared");
        model.addColumn("Level");

        reloadData();

        JLabel title = new JLabel("Players list");
        title.setFont(title.getFont().deriveFont(28f));
        title.setBorder(new EmptyBorder(new Insets(10,0,10,0)));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> this.dispose());


        add(title, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
        add(okButton, BorderLayout.PAGE_END);

        pack();
    }
}
