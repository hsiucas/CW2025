package com.comp2042.logic.brick;

import com.comp2042.model.bricks.core.Brick;
import com.comp2042.model.bricks.core.BrickGenerator;

/**
 * Manages the "Hold Piece" mechanic in the game.
 * Allows the player to store one brick for later use and swap it with the current active brick.
 */
public class BrickHolder {
    private Brick heldBrick;
    private boolean heldThisTurn = false;

    /**
     * Constructs a new BrickHolder with no initial held brick.
     */
    public BrickHolder() {
        this.heldBrick = null;
        this.heldThisTurn = false;
    }

    /**
     * Attempts to hold the current brick.
     * If a brick is already held, it swaps the current brick with the held one.
     * If no brick is held, it stores the current brick and spawns a new one.
     * A hold action can only be performed once per turn (until a piece locks).
     *
     * @param rotationState  The state object managing the current active brick.
     * @param brickGenerator The generator used to spawn a new brick if the hold slot is empty.
     * @return True if the hold action was successful, false if a hold was already used this turn.
     */
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

    /**
     * Resets the flag indicating a hold has occurred.
     * This should be called whenever a brick locks into place.
     */
    public void resetHold() {
        this.heldThisTurn = false;
    }

    /**
     * Retrieves the shape matrix of the currently held brick.
     *
     * @return The 2D integer array representing the held brick's shape, or null if no brick is held.
     */
    public int[][] getHeldBrickMatrix() {
        if (heldBrick == null) return null;
        return heldBrick.getShapeMatrix().get(0);
    }
}