package com.comp2042.logic.rules;

/**
 * Implements the rules for 4-Way (Welltris-style) Mode.
 * Gravity is essentially disabled (extremely slow) to allow the player to move the brick
 * in any direction freely before manually locking it or hitting a wall.
 */
public class FourWayRules implements GameModeRules {
    private static final double FOUR_WAY_GRAVITY = 999999;

    @Override
    public double getInitialSpeedDelay() {
        return FOUR_WAY_GRAVITY;
    }

    @Override
    public Boolean shouldLevelUp(int linesCleared, int currentLevel) {
        return false;
    }

    @Override
    public double getSpeedDelay(int currentLevel) {
        return FOUR_WAY_GRAVITY;
    }

    @Override
    public int getInitialLevel() {
        return 1;
    }

    @Override
    public boolean isHardDropAllowed() {
        return false;
    }

    @Override
    public boolean isGhostBrickAllowed() {
        return false;
    }
}
