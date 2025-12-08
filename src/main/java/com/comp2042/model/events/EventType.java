package com.comp2042.model.events;

/**
 * Enumeration representing the different types of actions or events that can occur in the game.
 */
public enum EventType {
    /** Movement downwards (soft drop). */
    DOWN,
    /** Movement to the left. */
    LEFT,
    /** Movement to the right. */
    RIGHT,
    /** Rotation action. */
    ROTATE,
    /** Holding the current brick. */
    HOLD,
    /** Instantly dropping the brick. */
    HARD_DROP,
    /** Movement upwards (used in 4-Way mode). */
    UP
}