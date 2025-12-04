package com.comp2042.logic.rules;

public class SurvivalModeRules implements GameModeRules {
    private static final double SURVIVAL_DELAY = 300;

    @Override
    public double getInitialSpeedDelay() {
        return SURVIVAL_DELAY;
    }

    @Override
    public Boolean shouldLevelUp(int linesCleared, int currentLevel) {
        return false;
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
