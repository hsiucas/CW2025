package com.comp2042.model.bricks.tetromino;

import com.comp2042.model.bricks.core.Tetromino;

final class OBrick extends Tetromino {

    public OBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 4, 4, 0},
                {0, 4, 4, 0},
                {0, 0, 0, 0}
        });
    }
}
