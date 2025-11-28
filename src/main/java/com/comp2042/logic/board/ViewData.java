package com.comp2042.logic.board;

import java.util.List;

public final class ViewData {

    private final int[][] brickData;
    private final int yPosition;
    private final int xPosition;
    private final List<int[][]> nextBricksData;

    public ViewData(int[][] brickData, int yPosition, int xPosition, List<int[][]> nextBricksData) {
        this.brickData = brickData;
        this.yPosition = yPosition;
        this.xPosition = xPosition;
        this.nextBricksData = nextBricksData;
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
}
