package com.comp2042.logic.brick;

import com.comp2042.logic.collision.CollisionDetector;

import java.awt.*;

public class BrickMover {
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

    public int hardDrop(int[][] boardMatrix, int[][] brick, Point offset, CollisionDetector collisionDetector) {
        int rowsDropped = 0;
        while (collisionDetector.canMove(boardMatrix, brick, offset, 1, 0)) {
            offset.translate(0, 1);
            rowsDropped++;
        }
        return rowsDropped;
    }

    public boolean moveUp(int[][] boardMatrix, int[][] brick, Point offset, CollisionDetector collisionDetector) {
        if (collisionDetector.canMove(boardMatrix, brick, offset, -1, 0)) {
            offset.translate(0,-1);
            return true;
        }
        return false;
    }
}
