package com.comp2042.logic.rules;

/**
 * Implements the rules for Survival Mode.
 * Features a faster initial speed and works in conjunction with the GameController's
 * garbage row generation to challenge the player.
 */
public class SurvivalModeRules implements GameModeRules {
    private static final double SURVIVAL_DELAY = 300;
    private static final int LINES_PER_LEVEL = 10;

    @Override
    public double getInitialSpeedDelay() {
        return SURVIVAL_DELAY;
    }

    @Override
    public Boolean shouldLevelUp(int linesCleared, int currentLevel) {
        return linesCleared >= (currentLevel) * LINES_PER_LEVEL;
    }

    @Override
    public double getSpeedDelay(int currentLevel) {
        return SURVIVAL_DELAY;
    }

    @Override
    public int getInitialLevel() {
        return 1;
    }
}
