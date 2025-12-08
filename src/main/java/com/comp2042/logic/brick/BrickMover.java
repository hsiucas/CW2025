package com.comp2042.logic.brick;

import com.comp2042.logic.collision.CollisionDetector;

import java.awt.*;

/**
 * Handles the movement logic for the active brick.
 * Responsible for moving the brick in 4 directions and performing hard drops.
 */
public class BrickMover {

    /**
     * Attempts to move the brick down by one unit.
     *
     * @param boardMatrix       The current game board.
     * @param brick             The brick shape matrix.
     * @param offset            The current position of the brick (modified in place if successful).
     * @param collisionDetector The detector to validate the move.
     * @return True if the move was successful, false if blocked (e.g., hit the floor).
     */
    public boolean moveDown(int[][] boardMatrix, int[][] brick, Point offset, CollisionDetector collisionDetector) {
        if (collisionDetector.canMove(boardMatrix, brick, offset, 1, 0)) {
            offset.translate(0,1);
            return true;
        }
        return false;
    }

    /**
     * Attempts to move the brick left by one unit.
     *
     * @param boardMatrix       The current game board.
     * @param brick             The brick shape matrix.
     * @param offset            The current position of the brick (modified in place if successful).
     * @param collisionDetector The detector to validate the move.
     * @return True if the move was successful, false if blocked.
     */
    public boolean moveLeft(int[][] boardMatrix, int[][] brick, Point offset, CollisionDetector collisionDetector) {
        if (collisionDetector.canMove(boardMatrix, brick, offset, 0, -1)) {
            offset.translate(-1,0);
            return true;
        }
        return false;
    }

    /**
     * Attempts to move the brick right by one unit.
     *
     * @param boardMatrix       The current game board.
     * @param brick             The brick shape matrix.
     * @param offset            The current position of the brick (modified in place if successful).
     * @param collisionDetector The detector to validate the move.
     * @return True if the move was successful, false if blocked.
     */
    public boolean moveRight(int[][] boardMatrix, int[][] brick, Point offset, CollisionDetector collisionDetector) {
        if (collisionDetector.canMove(boardMatrix, brick, offset, 0, 1)) {
            offset.translate(1,0);
            return true;
        }
        return false;
    }

    /**
     * Performs a hard drop, moving the brick down as far as possible instantly.
     *
     * @param boardMatrix       The current game board.
     * @param brick             The brick shape matrix.
     * @param offset            The current position of the brick (modified in place).
     * @param collisionDetector The detector to find the landing position.
     * @return The number of rows the brick was dropped.
     */
    public int hardDrop(int[][] boardMatrix, int[][] brick, Point offset, CollisionDetector collisionDetector) {
        int rowsDropped = 0;
        while (collisionDetector.canMove(boardMatrix, brick, offset, 1, 0)) {
            offset.translate(0, 1);
            rowsDropped++;
        }
        return rowsDropped;
    }

    /**
     * Attempts to move the brick up by one unit.
     * Primarily used in 4-Way mode.
     *
     * @param boardMatrix       The current game board.
     * @param brick             The brick shape matrix.
     * @param offset            The current position of the brick (modified in place if successful).
     * @param collisionDetector The detector to validate the move.
     * @return True if the move was successful, false if blocked.
     */
    public boolean moveUp(int[][] boardMatrix, int[][] brick, Point offset, CollisionDetector collisionDetector) {
        if (collisionDetector.canMove(boardMatrix, brick, offset, -1, 0)) {
            offset.translate(0,-1);
            return true;
        }
        return false;
    }
}