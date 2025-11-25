package com.comp2042.logic;

import java.awt.*;

public class BrickSpawnHandler {
    public Point getGameboySpawnPoint(int[][] boardMatrix, int boardWidth, int[][] brick) {
        int brickWidth = brick[0].length;
        int x = (boardWidth - brickWidth) / 2;
        int y = 2;

        Point gameboySpawnPoint = new Point(x, y);

        if (MatrixOperations.intersect(boardMatrix, brick, gameboySpawnPoint.y, gameboySpawnPoint.x)) {
            return null;
        }
        return gameboySpawnPoint;
    }
}
