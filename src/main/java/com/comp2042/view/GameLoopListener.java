package com.comp2042.view;

import com.comp2042.logic.gravity.DownData;

/**
 * Interface for listening to game loop events.
 * Implemented by the GUI Controller to update the view on every tick.
 */
public interface GameLoopListener {

    /**
     * Called on every game tick update.
     *
     * @param downData The data resulting from the logic update (e.g., brick position).
     */
    void onTick(DownData downData);

    /**
     * Called when the game over condition is met.
     */
    void onGameOver();
}