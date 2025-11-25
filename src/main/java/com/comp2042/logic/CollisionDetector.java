package com.comp2042.logic;

import java.awt.Point;

public class CollisionDetector {
    public CollisionDetector() {}

    public boolean canMove(int[][] boardMatrix, int[][] shape, Point offset, int dx, int dy) {
        Point newPosition = new Point(offset);
        newPosition.translate(dx, dy);
        return !MatrixOperations.intersect(boardMatrix, shape, newPosition.x, newPosition.y);
    }

    public boolean canRotate(int[][] boardMatrix, int[][] rotatedShape, Point offset) {
        return !MatrixOperations.intersect(boardMatrix, rotatedShape, offset.x, offset.y);
    }
}
