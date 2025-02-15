package com.antekk.tetris.shapes;

import java.awt.*;

public interface ShadowShape {
    final BasicStroke shadowShapeBorder = new BasicStroke(2);
    final BasicStroke defaultBorder = new BasicStroke(1);

    void drawAsShadow(Graphics2D g);

    void reloadShadow();
}
