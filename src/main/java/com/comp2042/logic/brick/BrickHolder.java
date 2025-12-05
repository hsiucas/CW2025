package com.comp2042.logic.brick;

import com.comp2042.model.bricks.core.Brick;
import com.comp2042.model.bricks.core.BrickGenerator;

public class BrickHolder {
    private Brick heldBrick;
    private boolean heldThisTurn = false;

    public BrickHolder() {
        this.heldBrick = null;
        this.heldThisTurn = false;
    }

    public boolean holdBrick(RotationState rotationState, BrickGenerator brickGenerator) {
        if (heldThisTurn) return false;
        Brick currentBrick = rotationState.getCurrentBrick();

        if (heldBrick == null) {
            heldBrick = currentBrick;
            rotationState.setBrick(brickGenerator.getBrick());
        } else {
            Brick temp = currentBrick;
            rotationState.setBrick(heldBrick);
            heldBrick = temp;
        }
        heldThisTurn = true;
        return true;
    }

    public void resetHold() {
        this.heldThisTurn = false;
    }

    public int[][] getHeldBrickMatrix() {
        if (heldBrick == null) return null;
        return heldBrick.getShapeMatrix().get(0);
    }
}
