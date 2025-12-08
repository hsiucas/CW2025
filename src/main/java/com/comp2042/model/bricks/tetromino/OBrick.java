package com.comp2042.model.bricks.tetromino;

import com.comp2042.model.bricks.core.Tetromino;

/**
 * Represents the 'O' shaped Tetromino.
 * Defines the 4x4 matrix for all rotation states of this specific brick.
 */
public final class OBrick extends Tetromino {

    /**
     * Constructs an OBrick.
     * Only one matrix as rotation does not change shape.
     * The value represents the respective color in the rendering system in BrickColourFactory.
     */
    public OBrick() {
        super.brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 4, 4, 0},
                {0, 4, 4, 0},
                {0, 0, 0, 0}
        });
    }
}
