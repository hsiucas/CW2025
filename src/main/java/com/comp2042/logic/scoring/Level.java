package com.comp2042.logic.scoring;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Manages the current game level.
 * Provides an observable property for UI updates.
 */
public final class Level {
    private final IntegerProperty level = new SimpleIntegerProperty(1);

    /**
     * Increments the current level by 1.
     */
    public void increment() {
        level.set(level.get() + 1);
    }

    /**
     * Gets the current level as an integer.
     *
     * @return The current level.
     */
    public int getLevel() {
        return level.get();
    }

    /**
     * Retrieves the observable level property.
     *
     * @return The IntegerProperty for the level.
     */
    public IntegerProperty levelProperty() {
        return level;
    }
}