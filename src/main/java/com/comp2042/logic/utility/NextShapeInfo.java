package com.comp2042.logic.utility;

import com.comp2042.logic.board.MatrixOperations;

/**
 * A utility class that holds information about a brick's next rotation state.
 * Used to calculate if a rotation is valid before applying it.
 */
public final class NextShapeInfo {

    private final int[][] shape;
    private final int position;

    /**
     * Constructs a new NextShapeInfo object.
     *
     * @param shape    The matrix of the brick in its next rotation.
     * @param position The index of the rotation state (0-3).
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    /**
     * Gets a copy of the shape matrix.
     *
     * @return The integer matrix.
     */
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * Gets the rotation index position.
     *
     * @return The position index.
     */
    public int getPosition() {
        return position;
    }
}