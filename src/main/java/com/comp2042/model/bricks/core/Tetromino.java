package com.comp2042.model.bricks.core;

import com.comp2042.logic.board.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all standard Tetromino shapes (I, J, L, O, S, T, Z).
 * Implements the common functionality for retrieving the shape matrix.
 */
public abstract class Tetromino implements Brick {

    /**
     * Holds the matrices for all 4 rotation states of the tetromino.
     */
    protected final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Retrieves the shape matrices.
     * Returns a deep copy to prevent external modification of the brick's definition.
     *
     * @return A list of cloned shape matrices.
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}