package com.comp2042.logic;

public final class ViewData {

    private final int[][] brickData;
    private final int yPosition;
    private final int xPosition;
    private final int[][] nextBrickData;

    public ViewData(int[][] brickData, int yPosition, int xPosition, int[][] nextBrickData) {
        this.brickData = brickData;
        this.yPosition = yPosition;
        this.xPosition = xPosition;
        this.nextBrickData = nextBrickData;
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

    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }
}
