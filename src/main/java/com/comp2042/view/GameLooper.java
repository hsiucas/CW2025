package com.comp2042.view;

import com.comp2042.logic.gravity.DownData;
import com.comp2042.model.events.InputEventListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Manages the main game loop using a JavaFX Timeline.
 * Triggers game logic updates at regular intervals based on the game speed.
 */
public class GameLooper {

    private final Timeline timeLine;
    private final InputEventListener eventListener;
    private final GameLoopListener listener;

    /**
     * Constructs a new GameLooper.
     *
     * @param listener      The listener to notify of updates (View).
     * @param eventListener The listener to trigger logic updates (Controller).
     * @param speed         The delay in milliseconds between ticks.
     */
    public GameLooper(GameLoopListener listener, InputEventListener eventListener, double speed) {
        this.listener = listener;
        this.eventListener = eventListener;

        this.timeLine = new Timeline(new KeyFrame(Duration.millis(speed), e -> {
            DownData downData = eventListener.onTick();
            listener.onTick(downData);

            if (downData.isGameOver()) listener.onGameOver();
        }));
        this.timeLine.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Starts the game loop.
     */
    public void start() {
        timeLine.play();
    }

    /**
     * Stops the game loop completely.
     */
    public void stop() {
        timeLine.stop();
    }

    /**
     * Pauses the game loop (can be resumed).
     */
    public void pause() {
        timeLine.pause();
    }

    /**
     * Resumes the game loop from a paused state.
     */
    public void resume() {
        timeLine.play();
    }
}