package com.comp2042.model.bricks.tetromino;

import com.comp2042.model.bricks.core.Tetromino;

final class LBrick extends Tetromino {

    public LBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {3, 3, 3, 0},
                {3, 0, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 3, 0, 0},
                {0, 3, 0, 0},
                {0, 3, 3, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 3, 0},
                {3, 3, 3, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {3, 3, 0, 0},
                {0, 3, 0, 0},
                {0, 3, 0, 0},
                {0, 0, 0, 0}
        });
    }
}
