package com.comp2042.controller;

import com.comp2042.application.AppNavigator;
import com.comp2042.logic.board.ViewData;
import com.comp2042.logic.gravity.DownData;
import com.comp2042.model.events.EventSource;
import com.comp2042.model.events.EventType;
import com.comp2042.model.events.InputEventListener;
import com.comp2042.model.events.MoveEvent;
import com.comp2042.view.GuiController;
import javafx.beans.property.BooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Handles raw keyboard input during the game loop.
 * Converts JavaFX KeyEvents into semantic game events (like MOVE_LEFT, ROTATE)
 * and dispatches them to the {@link InputEventListener}.
 */
public class KeyInputHandler implements EventHandler<KeyEvent> {
    private final InputEventListener eventListener;
    private final Runnable pauseToggleAction;
    private final BooleanProperty isPaused;
    private final BooleanProperty isGameOver;
    private final GuiController guiController;

    /**
     * Constructs a new KeyInputHandler.
     *
     * @param eventListener     The listener that processes game logic events.
     * @param pauseToggleAction A runnable to execute when the pause key is pressed.
     * @param isPaused          Property indicating if the game is currently paused.
     * @param isGameOver        Property indicating if the game is over.
     * @param guiController     The main GUI controller for accessing game state/mode.
     */
    public KeyInputHandler(InputEventListener eventListener, Runnable pauseToggleAction, BooleanProperty isPaused, BooleanProperty isGameOver,  GuiController guiController) {
        this.eventListener = eventListener;
        this.pauseToggleAction = pauseToggleAction;
        this.isPaused = isPaused;
        this.isGameOver = isGameOver;
        this.guiController = guiController;
    }

    /**
     * Handles the key press event.
     * Maps keys (WASD, Arrows, Space, etc.) to specific game actions based on the current Game Mode.
     *
     * @param keyEvent The key event to process.
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        if (isPaused.get() || isGameOver.get()) {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                pauseToggleAction.run();
            }
            return;
        }

        MoveEvent event = null;

        if      (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A)
            event = new MoveEvent(EventType.LEFT, EventSource.USER);
        else if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D)
            event = new MoveEvent(EventType.RIGHT, EventSource.USER);
        else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S)
            event = new MoveEvent(EventType.DOWN, EventSource.USER);
        else if (keyEvent.getCode() == KeyCode.SHIFT || keyEvent.getCode() == KeyCode.TAB)
            event = new MoveEvent(EventType.HOLD, EventSource.USER);
        else if (keyEvent.getCode() == KeyCode.N)
            eventListener.createNewGame();
        else if (keyEvent.getCode() == KeyCode.ESCAPE || keyEvent.getCode() == KeyCode.P)
            pauseToggleAction.run();
        else if (keyEvent.getCode() == KeyCode.BACK_SPACE)
            guiController.returnToMenu();

        if (guiController.getCurrentGameMode() == AppNavigator.GameMode.FOUR_WAY) {
            if      (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W)
                event = new MoveEvent(EventType.UP, EventSource.USER);
            else if (keyEvent.getCode() == KeyCode.SPACE)
                event = new MoveEvent(EventType.ROTATE, EventSource.USER);
        } else {
            if      (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W)
                event = new MoveEvent(EventType.ROTATE, EventSource.USER);
            else if (keyEvent.getCode() == KeyCode.SPACE)
                event = new MoveEvent(EventType.HARD_DROP, EventSource.USER);
        }

        if (event != null) {
            if (event.getEventType() == EventType.DOWN) {
                DownData down = eventListener.onDownEvent(event);
                guiController.getRenderer().refreshBrick(down.getViewData());
            } else if (event.getEventType() == EventType.HOLD) {
                ViewData data = eventListener.onHoldBrickEvent(event);
                guiController.getRenderer().refreshBrick(data);
            } else if (event.getEventType() == EventType.HARD_DROP) {
                DownData drop = eventListener.onHardDropEvent(event);
                guiController.getRenderer().refreshBrick(drop.getViewData());
            } else {
                guiController.handleMoveEvent(event);
            }
        }
        keyEvent.consume();
    }
}
