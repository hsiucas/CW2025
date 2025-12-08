package com.comp2042.model.events;

/**
 * Represents an event triggered by a move command.
 * Encapsulates the type of movement and the source of the event.
 */
public final class MoveEvent {
    private final EventType eventType;
    private final EventSource eventSource;

    /**
     * Constructs a new MoveEvent.
     *
     * @param eventType   The type of movement (e.g., LEFT, ROTATE).
     * @param eventSource The source of the event (e.g., USER, THREAD).
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Gets the type of the event.
     *
     * @return The {@link EventType}.
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Gets the source of the event.
     *
     * @return The {@link EventSource}.
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}