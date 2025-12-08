package com.comp2042.logic.rules;

/**
 * Implements a relaxed "Zen" mode.
 * The speed remains constant regardless of the level, allowing for endless, stress-free play.
 */
public class ZenModeRules implements GameModeRules {
    private static final double ZEN_DELAY = 800;
    private static final int LINES_PER_LEVEL = 10;

    @Override
    public double getInitialSpeedDelay() {
        return ZEN_DELAY;
    }

    @Override
    public Boolean shouldLevelUp(int linesCleared, int currentLevel) {
        return linesCleared >= (currentLevel) * LINES_PER_LEVEL;
    }

    /**
     * Returns a constant speed delay, ignoring the current level.
     */
    @Override
    public double getSpeedDelay(int currentLevel) {
        return ZEN_DELAY;
    }

    @Override
    public int getInitialLevel() {
        return 1;
    }
}
