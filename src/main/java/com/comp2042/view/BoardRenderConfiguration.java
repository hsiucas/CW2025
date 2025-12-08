package com.comp2042.view;

/**
 * Configuration class for rendering the game board.
 * It defines offsets and hidden rows to handle different board styles (Standard vs 4-Way).
 */
public class BoardRenderConfiguration {
    private static final int GAME_BOY_SPAWN = 2;

    private final double xOffset;
    private final double yOffset;
    private final int hiddenTopRows;

    /**
     * Constructs a new render configuration.
     *
     * @param xOffset       The horizontal offset for rendering the board.
     * @param yOffset       The vertical offset for rendering the board.
     * @param hiddenTopRows The number of top rows to hide (for spawn area).
     */
    public BoardRenderConfiguration(double xOffset, double yOffset, int hiddenTopRows) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.hiddenTopRows = hiddenTopRows;
    }

    /**
     * Creates a configuration for the standard Tetris board.
     * Hides the top 2 spawn rows to mimic Gameboy Tetris.
     *
     * @return A standard BoardRenderConfiguration.
     */
    public static BoardRenderConfiguration standard() {
        return new BoardRenderConfiguration(0,0, GAME_BOY_SPAWN);
    }

    /**
     * Creates a configuration for the 4-Way board.
     * Displays all rows as the spawn is in the center.
     *
     * @return A 4-Way BoardRenderConfiguration.
     */
    public static BoardRenderConfiguration fourWay() {
        return new BoardRenderConfiguration(0,0,0);
    }

    /**
     * Gets the X offset.
     * @return The X offset value.
     */
    public double getxOffset() {
        return xOffset;
    }

    /**
     * Gets the Y offset.
     * @return The Y offset value.
     */
    public double getyOffset() {
        return yOffset;
    }

    /**
     * Gets the count of hidden top rows.
     * @return The number of hidden rows.
     */
    public int getHiddenTopRows() {
        return hiddenTopRows;
    }
}