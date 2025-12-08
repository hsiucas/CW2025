package com.comp2042.logic.collision;

import com.comp2042.logic.board.MatrixOperations;

import java.awt.Point;

/**
 * Responsible for checking if a proposed movement or rotation of a brick is valid.
 * It verifies boundaries and checks for intersections with existing blocks on the board.
 */
public class CollisionDetector {

    public CollisionDetector() {}

    /**
     * Checks if a brick can move to a specific offset from its current position.
     *
     * @param boardMatrix The current game board matrix.
     * @param shape       The shape matrix of the brick.
     * @param offset      The current position of the brick.
     * @param dy          The change in Y (vertical movement).
     * @param dx          The change in X (horizontal movement).
     * @return True if the move is valid (no collision), false otherwise.
     */
    public boolean canMove(int[][] boardMatrix, int[][] shape, Point offset, int dy, int dx) {
        Point newPosition = new Point(offset);
        newPosition.translate(dx, dy);
        return !MatrixOperations.intersect(boardMatrix, shape, newPosition.y, newPosition.x);
    }

    /**
     * Checks if a brick can rotate to a new shape orientation.
     *
     * @param boardMatrix  The current game board matrix.
     * @param rotatedShape The shape matrix of the brick <i>after</i> rotation.
     * @param offset       The current position of the brick.
     * @return True if the rotation is valid (no collision), false otherwise.
     */
    public boolean canRotate(int[][] boardMatrix, int[][] rotatedShape, Point offset) {
        return !MatrixOperations.intersect(boardMatrix, rotatedShape, offset.y, offset.x);
    }
}