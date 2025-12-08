package com.comp2042.logic.collision;

import com.comp2042.logic.board.MatrixOperations;

import java.awt.*;

/**
 * Handles the logic when a brick lands (locks) on the board.
 * This includes merging the brick into the board matrix and checking for completed rows.
 */
public class BrickLandingHandler {

    /**
     * Merges the landing brick into the background board matrix.
     *
     * @param boardMatrix   The current board matrix.
     * @param brick         The shape matrix of the landing brick.
     * @param currentOffset The position where the brick landed.
     * @return A new board matrix containing the merged brick.
     */
    public int[][] mergeBrick(int[][] boardMatrix, int[][] brick, Point currentOffset) {
        return MatrixOperations.merge(boardMatrix, brick, currentOffset.y, currentOffset.x);
    }

    /**
     * Checks the board for any complete rows that need to be cleared.
     *
     * @param boardMatrix The current board matrix (after merging).
     * @return A {@link ClearRow} object containing the updated matrix and score details.
     */
    public ClearRow handleClearRows(int[][] boardMatrix) {
        return MatrixOperations.checkRemoving(boardMatrix);
    }
}