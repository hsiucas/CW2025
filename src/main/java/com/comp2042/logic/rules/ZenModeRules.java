package com.comp2042.logic.rules;

public class ZenModeRules implements GameModeRules {
    private static final double ZEN_DELAY = 800;

    @Override
    public double getInitialSpeedDelay() {
        return ZEN_DELAY;
    }

    @Override
    public Boolean shouldLevelUp(int linesCleared, int currentLevel) {
        return false;
    }

    @Override
    public double getSpeedDelay(int currentLevel) {
        return ZEN_DELAY;
    }

    @Override
    public int getInitialLevel() {
        return 1;
    }
}
