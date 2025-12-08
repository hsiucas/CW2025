package com.comp2042.logic.board;

import com.comp2042.logic.collision.ClearRow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A utility class providing static methods for 2D array manipulations used in the game logic.
 * Handles collision detection, merging, clearing rows, and generating garbage rows.
 */
public class MatrixOperations {

    private MatrixOperations() {}

    /**
     * Checks if a brick at a specific position intersects with existing blocks in the matrix.
     *
     * @param matrix The game board matrix.
     * @param brick  The brick shape matrix.
     * @param y      The Y coordinate of the brick.
     * @param x      The X coordinate of the brick.
     * @return True if a collision is detected or the brick is out of bounds, false otherwise.
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int y, int x) {
        for (int row = 0; row < brick.length; row++) {
            for (int col = 0; col < brick[row].length; col++) {
                int targetX = x + col;
                int targetY = y + row;
                if (brick[row][col] != 0 && (checkOutOfBound(matrix, targetY, targetX) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper method to check if coordinates are outside the board boundaries.
     *
     * @param matrix  The game board matrix.
     * @param targetY The Y coordinate to check.
     * @param targetX The X coordinate to check.
     * @return True if out of bounds.
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetY, int targetX) {
        return  targetY < 0 ||
                targetY >= matrix.length ||
                targetX < 0 ||
                targetX >= matrix[0].length;
    }

    /**
     * Creates a deep copy of a 2D integer array.
     *
     * @param original The array to copy.
     * @return A new array with the same contents.
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    /**
     * Merges a brick into the game board matrix.
     *
     * @param filledFields The current game board matrix.
     * @param brick        The brick shape matrix.
     * @param y            The Y coordinate.
     * @param x            The X coordinate.
     * @return A new matrix representing the board after the merge.
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int y, int x) {
        int[][] copy = copy(filledFields);
        for (int row = 0; row < brick.length; row++) {
            for (int col = 0; col < brick[row].length; col++) {
                int targetX = x + col;
                int targetY = y + row;
                if (brick[row][col] != 0) {
                    copy[targetY][targetX] = brick[row][col];
                }
            }
        }
        return copy;
    }

    /**
     * Checks for completed rows in the matrix and removes them.
     * Calculates the score bonus based on the number of cleared rows.
     *
     * @param matrix The game board matrix.
     * @return A {@link ClearRow} object containing the new matrix, lines removed, and score.
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0 || matrix[i][j] == 9) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }
        int scoreBonus = 50 * clearedRows.size() * clearedRows.size();
        return new ClearRow(clearedRows.size(), tmp, scoreBonus);
    }

    /**
     * Creates a deep copy of a list of 2D integer arrays.
     *
     * @param list The list to copy.
     * @return A new list containing deep copies of the arrays.
     */
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

    /**
     * Adds a "garbage" row to the bottom of the matrix, pushing existing blocks up.
     * Used for Survival Mode.
     *
     * @param originalMatrix   The current board matrix.
     * @param isIndestructible If true, the row is solid (cannot be cleared). If false, it has a random hole.
     * @return The new matrix with the added row.
     */
    public static int[][] addGarbageRow(int[][] originalMatrix, boolean isIndestructible) {
        int height = originalMatrix.length;
        int width = originalMatrix[0].length;
        int [][] newMatrix = new int[height][width];

        for (int row = 0; row < height - 1; row++) {
            System.arraycopy(originalMatrix[row + 1], 0, newMatrix[row], 0, width);
        }

        if (isIndestructible) {
            Arrays.fill(newMatrix[height - 1], 9);
        } else {
            Arrays.fill(newMatrix[height - 1], 8);
            int gapIndex = ThreadLocalRandom.current().nextInt(width);
            newMatrix[height - 1][gapIndex] = 0;
        }
        return newMatrix;
    }

    /**
     * Checks if the game is over by detecting blocks in the topmost row.
     *
     * @param matrix The game board matrix.
     * @return True if blocks exist in the top row, false otherwise.
     */
    public static boolean isGameOver(int[][] matrix) {
        for (int col = 0; col < matrix[0].length; col++) {
            if (matrix[0][col] != 0) return true;
        }
        return false;
    }
}