package com.comp2042.model.bricks.tetromino;

import com.comp2042.model.bricks.core.Tetromino;

final class ZBrick extends Tetromino {

    public ZBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {7, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 7, 0},
                {0, 7, 7, 0},
                {0, 7, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {7, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 7, 0, 0},
                {7, 7, 0, 0},
                {7, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }
}