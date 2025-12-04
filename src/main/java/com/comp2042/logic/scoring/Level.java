package com.comp2042.logic.scoring;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public final class Level {
    private final IntegerProperty level = new SimpleIntegerProperty(1);

    public void increment() {
        level.set(level.get() + 1);
    }

    public int getLevel() {
        return level.get();
    }

    public IntegerProperty levelProperty() {
        return level;
    }
}
