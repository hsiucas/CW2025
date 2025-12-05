package com.comp2042.logic.rules;

public class ClassicModeRules implements GameModeRules {
    private static final double INITIAL_DELAY = 800;
    private static final double SPEED_DECREMENT = 25;
    private static final double MAX_SPEED_DELAY = 50;
    private static final int LINES_PER_LEVEL = 10;
    private static final int SPEED_CAP_LEVEL = 30;

    @Override
    public double getInitialSpeedDelay() {
        return INITIAL_DELAY;
    }

    @Override
    public Boolean shouldLevelUp(int linesCleared, int currentLevel) {
        return linesCleared >= (currentLevel) * LINES_PER_LEVEL;
    }

    @Override
    public double getSpeedDelay(int currentLevel) {
        if (currentLevel >= SPEED_CAP_LEVEL) {
            return MAX_SPEED_DELAY;
        }

        double newSpeed = INITIAL_DELAY - (currentLevel * SPEED_DECREMENT);
        return Math.max(newSpeed, MAX_SPEED_DELAY);
    }

    @Override
    public int getInitialLevel() {
        return 1;
    }

    @Override
    public boolean isHoldBrickAllowed() { return false; }

    @Override
    public boolean isHardDropAllowed() { return false; }
}
