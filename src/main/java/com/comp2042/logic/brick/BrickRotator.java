package com.comp2042.logic.brick;

import com.comp2042.logic.collision.CollisionDetector;

import java.awt.*;

/**
 * Handles the logic for rotating bricks.
 * Checks if a rotation is valid before applying it to the rotation state.
 */
public class BrickRotator {

    /**
     * Attempts to rotate the current brick counter-clockwise.
     *
     * @param boardMatrix       The current game board.
     * @param rotationState     The state object managing the brick's rotation.
     * @param offset            The current position of the brick.
     * @param collisionDetector The detector to validate the rotation.
     * @return True if the rotation was successful, false if blocked.
     */
    public boolean rotateCounterClockwise(int[][] boardMatrix, RotationState rotationState, Point offset, CollisionDetector collisionDetector) {

        int[][] nextShape = rotationState.getNextShape().getShape();

        if (collisionDetector.canRotate(boardMatrix, nextShape, offset)) {
            rotationState.setCurrentShape(rotationState.getNextShape().getPosition());
            return true;
        }

        return false;
    }
}