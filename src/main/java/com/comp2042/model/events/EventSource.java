package com.comp2042.model.events;

/**
 * Enumeration indicating the origin of a game event.
 */
public enum EventSource {
    /**
     * Indicates the event was triggered by direct user input (e.g., keyboard press).
     */
    USER,

    /**
     * Indicates the event was triggered by an internal game process or thread
     * (e.g., automatic gravity ticks).
     */
    THREAD
}