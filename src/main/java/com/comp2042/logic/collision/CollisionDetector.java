package com.comp2042.logic.collision;

import com.comp2042.logic.board.MatrixOperations;

import java.awt.Point;

public class CollisionDetector {
    public CollisionDetector() {}

    public boolean canMove(int[][] boardMatrix, int[][] shape, Point offset, int dy, int dx) {
        Point newPosition = new Point(offset);
        newPosition.translate(dx, dy);
        return !MatrixOperations.intersect(boardMatrix, shape, newPosition.y, newPosition.x);
    }

    public boolean canRotate(int[][] boardMatrix, int[][] rotatedShape, Point offset) {
        return !MatrixOperations.intersect(boardMatrix, rotatedShape, offset.y, offset.x);
    }
}
