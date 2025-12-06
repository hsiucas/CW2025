package com.comp2042.logic.rules;

public interface GameModeRules {
    double getInitialSpeedDelay();
    Boolean shouldLevelUp(int linesCleared, int currentLevel);
    double getSpeedDelay(int currentLevel);
    int getInitialLevel();

    default boolean isHoldBrickAllowed() { return true; }
    default boolean isHardDropAllowed() { return true; }
    default boolean isGhostBrickAllowed() { return true;}
}
