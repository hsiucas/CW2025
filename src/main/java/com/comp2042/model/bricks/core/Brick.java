package com.comp2042.model.bricks.core;

import java.util.List;

/**
 * Interface representing a generic game piece (Brick).
 * Every brick is composed of a list of integer matrices, representing its
 * different rotation states.
 */
public interface Brick {
    /**
     * Gets the list of 2D matrices representing the brick's shape rotations.
     *
     * @return A List of int[][] arrays.
     */
    List<int[][]> getShapeMatrix();
}