package com.comp2042.logic;

import java.awt.*;

public class BrickMoverHandler {
    public boolean moveDown(int[][] boardMatrix, int[][] brick, Point offset, CollisionDetector collisionDetector) {
        if (collisionDetector.canMove(boardMatrix, brick, offset, 1, 0)) {
            offset.translate(0,1);
            return true;
        }
        return false;
    }

    public boolean moveLeft(int[][] boardMatrix, int[][] brick, Point offset, CollisionDetector collisionDetector) {
        if (collisionDetector.canMove(boardMatrix, brick, offset, 0, -1)) {
            offset.translate(-1,0);
            return true;
        }
        return false;
    }

    public boolean moveRight(int[][] boardMatrix, int[][] brick, Point offset, CollisionDetector collisionDetector) {
        if (collisionDetector.canMove(boardMatrix, brick, offset, 0, 1)) {
            offset.translate(1,0);
            return true;
        }
        return false;
    }
}
