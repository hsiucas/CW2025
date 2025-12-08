package com.comp2042.logic.board;

import java.util.List;

/**
 * An immutable Data Transfer Object (DTO) containing the state of the game board required for rendering.
 * This decouples the game logic from the view rendering code.
 */
public final class ViewData {

    private final int[][] brickData;
    private final int yPosition;
    private final int xPosition;
    private final List<int[][]> nextBricksData;
    private final int[][] heldBrickData;
    private final int ghostYPosition;

    /**
     * Constructs a new ViewData object.
     *
     * @param brickData      The matrix of the currently falling brick.
     * @param yPosition      The Y-coordinate of the falling brick.
     * @param xPosition      The X-coordinate of the falling brick.
     * @param nextBricksData A list of matrices for the upcoming bricks.
     * @param heldBrickData  The matrix of the currently held brick.
     * @param ghostYPosition The calculated Y-coordinate where the brick would land (Ghost Brick).
     */
    public ViewData(int[][] brickData, int yPosition, int xPosition, List<int[][]> nextBricksData, int[][] heldBrickData, int ghostYPosition) {
        this.brickData = brickData;
        this.yPosition = yPosition;
        this.xPosition = xPosition;
        this.nextBricksData = nextBricksData;
        this.heldBrickData = heldBrickData;
        this.ghostYPosition = ghostYPosition;
    }

    /**
     * Gets the shape matrix of the active brick.
     * @return A copy of the brick's matrix.
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * Gets the current Y position of the active brick.
     * @return The Y coordinate.
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * Gets the current X position of the active brick.
     * @return The X coordinate.
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * Gets the list of upcoming brick shapes for the preview panel.
     * @return A list of integer matrices.
     */
    public List<int[][]> getNextBricksData() {
        return nextBricksData;
    }

    /**
     * Gets the shape matrix of the held brick.
     * @return A copy of the held brick's matrix, or null if empty.
     */
    public int[][] getHeldBrickData() {
        if (heldBrickData == null) {
            return null;
        }
        return MatrixOperations.copy(heldBrickData);
    }

    /**
     * Gets the Y position of the ghost brick (landing preview).
     * @return The Y coordinate for the ghost brick.
     */
    public int getGhostYPosition() {
        return ghostYPosition;
    }
}