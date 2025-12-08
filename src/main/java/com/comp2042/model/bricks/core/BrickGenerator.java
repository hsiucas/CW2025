package com.comp2042.model.bricks.core;

/**
 * Defines a strategy for generating new bricks.
 * Implementations can define random generation, bag systems, or deterministic sequences for testing.
 */
public interface BrickGenerator {

    /**
     * Generates and returns a new Brick.
     *
     * @return A new {@link Brick} instance.
     */
    Brick getBrick();

    /**
     * Peeks at the next brick that will be generated without removing it from the queue.
     *
     * @return The next {@link Brick} instance.
     */
    Brick getNextBrick();
}