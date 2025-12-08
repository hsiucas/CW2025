package com.comp2042.view;

import com.comp2042.application.AppNavigator;
import com.comp2042.controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Handles the visual animation played when the game ends.
 * Creates a "wiping" effect by clearing rows one by one before navigating away.
 */
public class GameOverAnimator {

    private final GameRenderer renderer;
    private final GameController controller;
    private final AppNavigator navigator;
    private final GameLooper looper;

    /**
     * Constructs a GameOverAnimator.
     *
     * @param renderer   The renderer to update the visual board state.
     * @param controller The game controller to modify the board state.
     * @param navigator  The navigator to switch screens after animation.
     * @param looper     The game loop to stop.
     */
    public GameOverAnimator(GameRenderer renderer, GameController controller, AppNavigator navigator, GameLooper looper) {
        this.renderer = renderer;
        this.controller = controller;
        this.navigator = navigator;
        this.looper = looper;
    }

    /**
     * Starts the Game Over sequence.
     * Stops the game loop and initiates the wipe animation after a short delay.
     */
    public void play() {
        if (looper != null) looper.stop();
        performDelay(1, this::wipeAnimation);
    }

    /**
     * Executes the row-by-row clearing animation.
     */
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

    /**
     * Helper to run a task after a specified delay.
     *
     * @param seconds  The delay in seconds.
     * @param runnable The task to run.
     */
    private void performDelay (double seconds, Runnable runnable) {
        PauseTransition delay = new PauseTransition(Duration.seconds(seconds));
        delay.setOnFinished(event -> runnable.run());
        delay.play();
    }
}