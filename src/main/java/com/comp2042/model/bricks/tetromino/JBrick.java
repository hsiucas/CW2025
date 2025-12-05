package com.comp2042.model.bricks.tetromino;

import com.comp2042.model.bricks.core.Tetromino;

public final class JBrick extends Tetromino {

    public JBrick() {
        super.brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {2, 2, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0}
        });
        super.brickMatrix.add(new int[][]{
                {0, 2, 2, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0}
        });
        super.brickMatrix.add(new int[][]{
                {2, 0, 0, 0},
                {2, 2, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        super.brickMatrix.add(new int[][]{
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {2, 2, 0, 0},
                {0, 0, 0, 0}
        });
    }
}
