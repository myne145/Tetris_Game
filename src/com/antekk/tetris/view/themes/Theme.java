package com.antekk.tetris.view.themes;

public enum Theme {
    LIGHT,
    DARK;


    @Override
    public String toString() {
        String s = super.toString();
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
