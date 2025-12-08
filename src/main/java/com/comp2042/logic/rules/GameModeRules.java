package com.comp2042.logic.rules;

/**
 * Defines the contract for game rules across different modes (Classic, Zen, Survival, etc.).
 * Implementations of this interface control speed, leveling, and allowed player actions.
 */
public interface GameModeRules {

    /**
     * Gets the initial speed delay (in milliseconds) for the game mode.
     *
     * @return The initial delay between gravity ticks.
     */
    double getInitialSpeedDelay();

    /**
     * Determines if the player should advance to the next level.
     *
     * @param linesCleared Total lines cleared so far.
     * @param currentLevel The current level of the player.
     * @return True if the level should increment, false otherwise.
     */
    Boolean shouldLevelUp(int linesCleared, int currentLevel);

    /**
     * Calculates the speed delay for a specific level.
     *
     * @param currentLevel The level to calculate speed for.
     * @return The delay in milliseconds.
     */
    double getSpeedDelay(int currentLevel);

    /**
     * Gets the starting level for the game mode.
     *
     * @return The initial level (usually 1).
     */
    int getInitialLevel();

    /**
     * Checks if the "Hold Brick" mechanic is allowed in this mode.
     *
     * @return True if allowed, false otherwise (default is true).
     */
    default boolean isHoldBrickAllowed() {
        return true;
    }

    /**
     * Checks if the "Hard Drop" mechanic is allowed in this mode.
     *
     * @return True if allowed, false otherwise (default is true).
     */
    default boolean isHardDropAllowed() {
        return true;
    }

    /**
     * Checks if the "Ghost Brick" (landing preview) is visible in this mode.
     *
     * @return True if visible, false otherwise (default is true).
     */
    default boolean isGhostBrickAllowed() {
        return true;
    }
}