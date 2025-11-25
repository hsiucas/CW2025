package com.comp2042.logic.brick;

import com.comp2042.logic.collision.CollisionDetector;

import java.awt.*;

public class BrickRotator {

    public boolean rotateCounterClockwise(int[][] boardMatrix, RotationState rotationState, Point offset, CollisionDetector collisionDetector) {

        int[][] nextShape = rotationState.getNextShape().getShape();

        if (collisionDetector.canRotate(boardMatrix, nextShape, offset)) {
            rotationState.setCurrentShape(rotationState.getNextShape().getPosition());
            return true;
        }

        return false;
    }
}
