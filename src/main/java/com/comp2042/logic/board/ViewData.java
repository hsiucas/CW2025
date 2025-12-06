package com.comp2042.logic.board;

import java.util.List;

public final class ViewData {

    private final int[][] brickData;
    private final int yPosition;
    private final int xPosition;
    private final List<int[][]> nextBricksData;
    private final int[][] heldBrickData;
    private final int ghostYPosition;

    public ViewData(int[][] brickData, int yPosition, int xPosition, List<int[][]> nextBricksData, int[][] heldBrickData, int ghostYPosition) {
        this.brickData = brickData;
        this.yPosition = yPosition;
        this.xPosition = xPosition;
        this.nextBricksData = nextBricksData;
        this.heldBrickData = heldBrickData;
        this.ghostYPosition = ghostYPosition;
    }

    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    public int getyPosition() {
        return yPosition;
    }

    public int getxPosition() {
        return xPosition;
    }

    public List<int[][]> getNextBricksData() {
        return nextBricksData;
    }

    public int[][] getHeldBrickData() {
        if (heldBrickData == null) {
            return null;
        }
        return MatrixOperations.copy(heldBrickData);
    }

    public int getGhostYPosition() {
        return ghostYPosition;
    }
}
