package com.comp2042.logic.scoring;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Wraps the count of cleared lines in the game.
 * Uses JavaFX properties to allow the UI to automatically update when lines are cleared.
 */
public final class Lines {

    private final IntegerProperty lines = new SimpleIntegerProperty(0);

    /**
     * Retrieves the observable lines property.
     *
     * @return The IntegerProperty representing total lines cleared.
     */
    public IntegerProperty linesProperty() {
        return lines;
    }

    /**
     * Adds a specific number of lines to the total count.
     *
     * @param i The number of lines to add.
     */
    public void add(int i){
        lines.setValue(lines.getValue() + i);
    }

    /**
     * Resets the line count to zero.
     */
    public void reset() {
        lines.setValue(0);
    }

    /**
     * Gets the current integer value of lines cleared.
     *
     * @return The total lines cleared.
     */
    public int getLines(){
        return lines.get();
    }
}