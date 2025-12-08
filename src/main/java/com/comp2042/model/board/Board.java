package com.comp2042.model.board;

import com.comp2042.logic.board.ViewData;
import com.comp2042.logic.collision.ClearRow;
import com.comp2042.logic.rules.GameModeRules;
import com.comp2042.logic.scoring.Level;
import com.comp2042.logic.scoring.Lines;
import com.comp2042.logic.scoring.Score;

/**
 * Defines the contract for a game board.
 * A board is responsible for managing the grid state, the active brick, collision checks,
 * and game state updates (score, lines, level).
 */
public interface Board {

    /**
     * Attempts to move the active brick down.
     * @return True if the move was successful, false otherwise.
     */
    boolean moveBrickDown();

    /**
     * Attempts to move the active brick left.
     * @return True if the move was successful, false otherwise.
     */
    boolean moveBrickLeft();

    /**
     * Attempts to move the active brick right.
     * @return True if the move was successful, false otherwise.
     */
    boolean moveBrickRight();

    /**
     * Attempts to move the active brick up (used in 4-Way mode).
     * @return True if the move was successful, false otherwise.
     */
    default boolean moveBrickUp() {
        return false;
    }

    /**
     * Attempts to rotate the active brick counter-clockwise.
     * @return True if the rotation was successful, false if blocked.
     */
    boolean rotateBrickCounterClockwise();

    /**
     * Spawns a new brick at the top (or center) of the board.
     * @return True if the brick spawned successfully, false if there was no space (Game Over).
     */
    boolean createNewBrick();

    /**
     * Gets the current grid state as a 2D integer array.
     * @return The board matrix.
     */
    int[][] getBoardMatrix();

    /**
     * Gets a snapshot of the board state for rendering.
     * @return A {@link ViewData} object containing brick positions and shapes.
     */
    ViewData getViewData();

    /**
     * Locks the active brick into the background matrix.
     */
    void mergeBrickToBackground();

    /**
     * Checks for and removes completed lines/rows.
     * @return A {@link ClearRow} object detailing removed lines and score.
     */
    ClearRow clearRows();

    /**
     * Gets the score manager.
     * @return The Score object.
     */
    Score getScore();

    /**
     * Gets the lines manager.
     * @return The Lines object.
     */
    Lines getLines();

    /**
     * Gets the level manager.
     * @return The Level object.
     */
    Level getLevel();

    /**
     * Sets the rules for the current game mode.
     * @param rules The GameModeRules to apply.
     */
    void setRules(GameModeRules rules);

    /**
     * Attempts to swap the current brick with the held brick.
     * @return True if the swap occurred, false if hold is locked for this turn.
     */
    boolean holdBrick();

    /**
     * Resets the board for a new game.
     */
    void newGame();

    /**
     * Sets a specific row to a specific value (used for visual effects or garbage rows).
     * @param row   The row index.
     * @param value The value to fill the row with.
     */
    void rowToValue(int row, int value);
}