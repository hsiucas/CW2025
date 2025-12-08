package com.comp2042.model.bricks.tetromino;

import com.comp2042.model.bricks.core.Tetromino;

/**
 * Represents the 'L' shaped Tetromino.
 * Defines the 4x4 matrix for all rotation states of this specific brick.
 */
public final class LBrick extends Tetromino {

    /**
     * Constructs an LBrick.
     * Initializes the matrix definitions for the 0, 90, 180, and 270 degree rotations.
     * The value represents the respective color in the rendering system in BrickColourFactory.
     */
    public LBrick() {
        super.brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {3, 3, 3, 0},
                {3, 0, 0, 0},
                {0, 0, 0, 0}
        });
        super.brickMatrix.add(new int[][]{
                {0, 3, 0, 0},
                {0, 3, 0, 0},
                {0, 3, 3, 0},
                {0, 0, 0, 0}
        });
        super.brickMatrix.add(new int[][]{
                {0, 0, 3, 0},
                {3, 3, 3, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        super.brickMatrix.add(new int[][]{
                {3, 3, 0, 0},
                {0, 3, 0, 0},
                {0, 3, 0, 0},
                {0, 0, 0, 0}
        });
    }
}
