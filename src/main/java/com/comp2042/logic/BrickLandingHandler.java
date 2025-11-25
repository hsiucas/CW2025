package com.comp2042.logic;

import java.awt.*;

public class BrickLandingHandler {
    public int[][] mergeBrick(int[][] boardMatrix, int[][] brick, Point currentOffset) {
        return MatrixOperations.merge(boardMatrix, brick, currentOffset.y, currentOffset.x);
    }

    public ClearRow handleClearRows(int[][] boardMatrix) {
        return MatrixOperations.checkRemoving(boardMatrix);
    }
}
