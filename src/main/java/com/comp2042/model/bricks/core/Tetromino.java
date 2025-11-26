package com.comp2042.model.bricks.core;

import com.comp2042.logic.board.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

public abstract class Tetromino implements Brick {

    protected final List<int[][]> brickMatrix = new ArrayList<>();

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
