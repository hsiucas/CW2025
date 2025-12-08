package com.comp2042.logic.collision;

/**
 * Handles the specialized line-clearing logic for the 4-Way game mode.
 * In this mode, lines are cleared towards the center of the board rather than strictly downwards.
 */
public class FourWayClearHandler {
    private static final int SCORE_BONUS = 55;

    /**
     * Checks for and clears full rows and columns in the 4-Way board matrix.
     * Cleared lines cause blocks to shift towards the center of the board.
     *
     * @param matrix The current game board matrix.
     * @return A {@link ClearRow} object with the updated matrix and calculated score.
     */
    public ClearRow handleClearFourWay(int[][] matrix) {
        int size = matrix.length;
        int linesCleared = 0;
        int scoreBonus = 0;

        for (int row = 0; row < size; row++) {
            if (isRowFull(matrix, row)) {
                clearRow(matrix, row);
                linesCleared++;
                scoreBonus += SCORE_BONUS;
            }
        }

        for (int col = 0; col < size; col++) {
            if (isColFull(matrix, col)) {
                clearCol(matrix, col);
                linesCleared++;
                scoreBonus += SCORE_BONUS;
            }
        }

        return new ClearRow(linesCleared, matrix, scoreBonus);
    }
    
    private boolean isRowFull(int[][] matrix, int row) {
        for (int col :  matrix[row]) {
            if (col == 0) return false;
        }
        return true;
    }

    private boolean isColFull(int[][] matrix, int col) {
        for (int[] row : matrix) {
            if (row[col] == 0) return false;
        }
        return true;
    }

    private void clearRow(int[][] matrix, int rowCleared) {
        int size = matrix.length;
        int center = size / 2;

        if (rowCleared < center) {
            for (int r = rowCleared; r < center - 1; r++) {
                matrix[r] = matrix[r + 1].clone();
            }
            for (int c = 0; c < size; c++) {
                matrix[center - 1][c] = 0;
            }
        } else {
            for (int r = rowCleared; r > center; r--) {
                matrix[r] = matrix[r - 1].clone();
            }
            for (int c = 0; c < size; c++) {
                matrix[center][c] = 0;
            }
        }
    }

    private void clearCol(int[][] matrix, int colCleared) {
        int size = matrix.length;
        int center = size / 2;

        if (colCleared < center) {
            for (int c = colCleared; c < center - 1; c++) {
                for (int r = 0; r < size; r++) {
                    matrix[r][c] = matrix[r][c + 1];
                }
            }
            for (int r = 0; r < size; r++) {
                matrix[r][center - 1] = 0;
            }
        } else {
            for (int c = colCleared; c > center; c--) {
                for (int r = 0; r < size; r++) {
                    matrix[r][c] = matrix[r][c - 1];
                }
            }
            for (int r = 0; r < size; r++) {
                matrix[r][center] = 0;
            }
        }
    }
}
