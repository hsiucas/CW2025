package com.comp2042.controller;

/**
 * Defines global constants and configuration settings for the game.
 * This class eliminates "magic numbers" related to board dimensions.
 */
public class GameConfiguration {

    /**
     * The height (number of rows) of the standard game board.
     */
    public static final int SIMPLE_BOARD_HEIGHT = 20;

    /**
     * The width (number of columns) of the standard game board.
     */
    public static final int SIMPLE_BOARD_WIDTH = 10;

    /**
     * The side length of the square board used in 4-Way mode.
     */
    public static final int FOUR_WAY_BOARD_LENGTH = 11;
}