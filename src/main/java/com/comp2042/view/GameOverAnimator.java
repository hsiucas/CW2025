package com.comp2042.view;

import com.comp2042.application.AppNavigator;
import com.comp2042.controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameOverAnimator {

    private final GameRenderer renderer;
    private final GameController controller;
    private final AppNavigator navigator;
    private final GameLooper looper;

    public GameOverAnimator(GameRenderer renderer, GameController controller, AppNavigator navigator, GameLooper looper) {
        this.renderer = renderer;
        this.controller = controller;
        this.navigator = navigator;
        this.looper = looper;
    }

    public void play() {
        if (looper != null) looper.stop();
        performDelay(1, this::wipeAnimation);
    }

    public void wipeAnimation() {
        int[][] matrix = controller.getBoardMatrix();
        int totalRows = matrix.length;

        Timeline wipeBoard = new Timeline();
        wipeBoard.setCycleCount(totalRows + 1);

        final int[] currentRow = {0};

        wipeBoard.getKeyFrames().add(new KeyFrame(Duration.millis(60), e -> {
            int row = currentRow[0];
            if (row < totalRows) {
                controller.clearRowWhenGameOver(row);
                renderer.refreshGameBackground(controller.getBoardMatrix());
                currentRow[0]++;
            } else {
                performDelay(2, () -> {
                    if (navigator != null) navigator.toGameModeSelection();
                });
            }
        }));
        wipeBoard.play();
    }

    private void performDelay (double seconds, Runnable runnable) {
        PauseTransition delay = new PauseTransition(Duration.seconds(seconds));
        delay.setOnFinished(event -> runnable.run());
        delay.play();
    }
}
