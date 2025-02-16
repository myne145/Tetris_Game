package com.antekk.tetris.shapes;

import java.awt.*;

public interface ShadowShape {
    BasicStroke shadowShapeBorder = new BasicStroke(2);
    BasicStroke defaultBorder = new BasicStroke(1);

    void drawAsShadow(Graphics2D g);

    void reloadShadow();
}
