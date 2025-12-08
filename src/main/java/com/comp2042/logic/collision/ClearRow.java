package com.comp2042.logic.collision;

import com.comp2042.logic.board.MatrixOperations;

/**
 * A Data Transfer Object (DTO) holding the results of a line-clearing operation.
 * It stores the number of lines removed, the resulting board matrix, and the score bonus.
 */
public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int scoreBonus;

    /**
     * Constructs a new ClearRow result.
     *
     * @param linesRemoved The number of lines that were cleared.
     * @param newMatrix    The updated board matrix after clearing lines.
     * @param scoreBonus   The score points awarded for this clear.
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    /**
     * Gets the number of lines removed.
     *
     * @return The count of removed lines.
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Gets the new board matrix state.
     *
     * @return A copy of the updated integer matrix.
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * Gets the score bonus associated with this clear action.
     *
     * @return The score bonus points.
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}