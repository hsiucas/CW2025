package com.comp2042.logic.gravity;

import com.comp2042.logic.board.ViewData;
import com.comp2042.logic.collision.ClearRow;

/**
 * A Data Transfer Object (DTO) that encapsulates the result of a gravity tick or a hard drop.
 * It contains information about cleared rows, the current state of the board for rendering,
 * and whether the game has ended.
 */
public final class DownData {
    private final ClearRow clearRow;
    private final ViewData viewData;
    private final boolean gameOver;

    /**
     * Constructs a new DownData object.
     *
     * @param clearRow  Information about any rows that were cleared during this tick.
     * @param viewData  The current visual state of the board and brick.
     * @param gameOver  True if the move resulted in a Game Over condition.
     */
    public DownData(ClearRow clearRow, ViewData viewData, boolean gameOver) {
        this.clearRow = clearRow;
        this.viewData = viewData;
        this.gameOver = gameOver;
    }

    /**
     * Gets the ClearRow object containing details about cleared lines and score bonuses.
     *
     * @return The ClearRow object, or null if no lines were cleared.
     */
    public ClearRow getClearRow() {
        return clearRow;
    }

    /**
     * Gets the ViewData object for rendering the game state.
     *
     * @return The ViewData object.
     */
    public ViewData getViewData() {
        return viewData;
    }

    /**
     * Checks if the game has ended.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }
}