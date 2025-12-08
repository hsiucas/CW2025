package com.comp2042.model.events;

import com.comp2042.logic.gravity.DownData;
import com.comp2042.logic.board.ViewData;

/**
 * Interface for handling game input events.
 * Implemented by controllers to respond to user actions (like key presses)
 * and game loop events (like gravity ticks).
 */
public interface InputEventListener {

    /**
     * Called on every game loop tick to advance the game state (gravity).
     *
     * @return The {@link DownData} containing the result of the tick (e.g., did the brick lock?).
     */
    DownData onTick();

    /**
     * Called when the "Down" action is triggered (e.g., soft drop).
     *
     * @param event The move event details.
     * @return The {@link DownData} containing the result of the move.
     */
    DownData onDownEvent(MoveEvent event);

    /**
     * Called when the "Left" action is triggered.
     *
     * @param event The move event details.
     * @return The updated {@link ViewData} for rendering.
     */
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Called when the "Right" action is triggered.
     *
     * @param event The move event details.
     * @return The updated {@link ViewData} for rendering.
     */
    ViewData onRightEvent(MoveEvent event);

    /**
     * Called when the "Rotate" action is triggered.
     *
     * @param event The move event details.
     * @return The updated {@link ViewData} reflecting the new rotation.
     */
    ViewData onRotateEvent(MoveEvent event);

    /**
     * Called when the "Hold" action is triggered to swap the active brick.
     *
     * @param event The move event details.
     * @return The updated {@link ViewData} reflecting the swapped brick.
     */
    ViewData onHoldBrickEvent(MoveEvent event);

    /**
     * Called when the "Hard Drop" action is triggered.
     *
     * @param event The move event details.
     * @return The {@link DownData} containing the result of the drop (typically locking).
     */
    DownData onHardDropEvent(MoveEvent event);

    /**
     * Called when the "Up" action is triggered (used in 4-Way mode).
     *
     * @param event The move event details.
     * @return The updated {@link ViewData} for rendering.
     */
    ViewData onUpEvent(MoveEvent event);

    /**
     * Resets the game state to start a new session.
     */
    void createNewGame();
}