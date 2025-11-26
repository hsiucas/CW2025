package com.comp2042.view;

import com.comp2042.logic.gravity.DownData;
import com.comp2042.model.events.InputEventListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameLooper {

    private static final double DEFAULT_SPEED_IN_MS = 400;

    private final Timeline timeLine;
    private final InputEventListener eventListener;
    private final GameLoopListener listener;

    public GameLooper(GameLoopListener listener, InputEventListener eventListener) {
        this.listener = listener;
        this.eventListener = eventListener;

        this.timeLine = new Timeline(new KeyFrame(Duration.millis(DEFAULT_SPEED_IN_MS), e -> {
            DownData downData = eventListener.onTick();
            listener.onTick(downData);

            if (downData.isGameOver()) listener.onGameOver();
        }));
        this.timeLine.setCycleCount(Timeline.INDEFINITE);
    }

    public void start() {
        timeLine.play();
    }

    public void stop() {
        timeLine.stop();
    }

    public void pause() {
        timeLine.pause();
    }

    public void resume() {
        timeLine.play();
    }
}
