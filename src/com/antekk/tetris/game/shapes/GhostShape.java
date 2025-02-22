package com.antekk.tetris.game.shapes;

import java.awt.*;

public interface GhostShape {
    BasicStroke ghostShapeBorder = new BasicStroke(2);
    BasicStroke defaultBorder = new BasicStroke(1);

    void drawGhostShape(Graphics2D g);

    void reloadGhostShape();
}
