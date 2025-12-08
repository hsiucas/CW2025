package com.comp2042.logic.brick;

import com.comp2042.model.bricks.core.Brick;
import com.comp2042.logic.utility.NextShapeInfo;

/**
 * Manages the rotation state of the active brick.
 * Tracks which rotation index (0, 90, 180, 270 degrees) the brick is currently in.
 */
public class RotationState {

    private Brick brick;
    private int currentShape = 0;

    /**
     * Calculates the information for the next rotation state (counter-clockwise).
     *
     * @return A {@link NextShapeInfo} object containing the matrix and index of the next rotation.
     */
    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Gets the current shape matrix of the brick based on its rotation.
     *
     * @return The 2D integer array for the current rotation.
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Sets the current rotation index.
     *
     * @param currentShape The index of the rotation to set.
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Sets the active brick and resets the rotation index to 0.
     *
     * @param brick The new {@link Brick} object.
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }

    /**
     * Gets the underlying Brick object.
     *
     * @return The current Brick.
     */
    public Brick getCurrentBrick() {
        return brick;
    }
}