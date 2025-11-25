package com.comp2042.logic;

import java.awt.*;

public class BrickRotatorHandler {

    public boolean rotateCounterClockwise(int[][] boardMatrix, BrickRotator brickRotator, Point offset, CollisionDetector collisionDetector) {

        int[][] nextShape = brickRotator.getNextShape().getShape();

        if (collisionDetector.canRotate(boardMatrix, nextShape, offset)) {
            brickRotator.setCurrentShape(brickRotator.getNextShape().getPosition());
            return true;
        }

        return false;
    }
}
