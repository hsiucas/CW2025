package com.comp2042.view;

public class BoardRenderConfiguration {
    private static final int GAME_BOY_SPAWN = 2;

    private final double xOffset;
    private final double yOffset;
    private final int hiddenTopRows;

    public BoardRenderConfiguration(double xOffset, double yOffset, int hiddenTopRows) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.hiddenTopRows = hiddenTopRows;
    }

    public static BoardRenderConfiguration standard() {
        return new BoardRenderConfiguration(0,0, GAME_BOY_SPAWN);
    }

    public static BoardRenderConfiguration fourWay() {
        return new BoardRenderConfiguration(0,0,0);
    }

    public double getxOffset() {
        return xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public int getHiddenTopRows() {
        return hiddenTopRows;
    }
}
