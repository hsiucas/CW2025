package com.comp2042.model.bricks.tetromino;

import com.comp2042.model.bricks.core.Tetromino;

/**
 * Represents the 'I' shaped Tetromino.
 * Defines the 4x4 matrix for all rotation states of this specific brick.
 */
public final class IBrick extends Tetromino {

    /**
     * Constructs an IBrick.
     * Initializes the matrix definitions for the 0, 90, 180, and 270 degree rotations.
     * The value represents the respective color in the rendering system in BrickColourFactory.
     */
    public IBrick() {
        super.brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        super.brickMatrix.add(new int[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        });
        super.brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0}
        });
        super.brickMatrix.add(new int[][]{
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0}
        });
    }
}
