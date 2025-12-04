package com.comp2042.logic.rules;

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
