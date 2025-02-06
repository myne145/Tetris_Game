package com.antekk.tetris.blocks;

import com.antekk.tetris.gameview.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public interface Shape {
    Color getColor();
    ArrayList<ArrayList<Block>> getBlocks();

    void rotateLeft();
    void rotateRight();
    void hardDrop();

    default void draw(Graphics g) {
        g.setColor(this.getColor());
        for(ArrayList<Block> line : this.getBlocks()) {
            for(Block block : line) {
                g.fillRect(block.x, block.y, Block.getSizePx(), Block.getSizePx());
            }
        }
    }

    default Block getBottomBlock() {
        ArrayList<Block> lastLine = getBlocks().getLast();
        return lastLine.getFirst();
    }

    default boolean moveDown() {
        if(getBottomBlock().y == (GamePanel.getBoardRows() - 1) * Block.getSizePx()) {
            return false;
        }

        for(ArrayList<Block> line : getBlocks()) {
            for(Block block : line) {
                block.y += Block.getSizePx();
            }
        }
        return true;
    }

    /**
     *
     * @param dir -1 is left, 1 is right
     * @return
     */
    private boolean moveHorizontaly(int dir) {
//        if(getBottomBlock().x == (GamePanel.getBoardRows() - 1) * Block.getSizePx()) {
//            return false;
//        } //TODO

        for(ArrayList<Block> line : getBlocks()) {
            for(Block block : line) {
                block.x += dir * Block.getSizePx();
            }
        }
        return true;
    }

    default boolean moveLeft() {
        return moveHorizontaly(-1);
    }

    default boolean moveRight() {
        return moveHorizontaly(1);
    }

    static float getSpeedBlocksPerSeconds() {
        return 4f;
    }
}
