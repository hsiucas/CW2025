package com.comp2042.controller;

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

public class KeyInputHandler implements EventHandler<KeyEvent> {
    private final InputEventListener eventListener;
    private final Runnable pauseToggleAction;
    private final BooleanProperty isPaused;
    private final BooleanProperty isGameOver;
    private final GuiController guiController;

    public KeyInputHandler(InputEventListener eventListener, Runnable pauseToggleAction, BooleanProperty isPaused, BooleanProperty isGameOver,  GuiController guiController) {
        this.eventListener = eventListener;
        this.pauseToggleAction = pauseToggleAction;
        this.isPaused = isPaused;
        this.isGameOver = isGameOver;
        this.guiController = guiController;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (isPaused.get() || isGameOver.get()) {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                pauseToggleAction.run();
            }
            return;
        }

        MoveEvent event = null;

        if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A)
            event = new MoveEvent(EventType.LEFT, EventSource.USER);
        else if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D)
            event = new MoveEvent(EventType.RIGHT, EventSource.USER);
        else if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W)
            event = new MoveEvent(EventType.ROTATE, EventSource.USER);
        else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S)
            event = new MoveEvent(EventType.DOWN, EventSource.USER);
        else if (keyEvent.getCode() == KeyCode.SHIFT || keyEvent.getCode() == KeyCode.TAB)
            event = new MoveEvent(EventType.HOLD, EventSource.USER);
        else if (keyEvent.getCode() == KeyCode.N)
            eventListener.createNewGame();
        else if (keyEvent.getCode() == KeyCode.ESCAPE)
            pauseToggleAction.run();

        if (event != null) {
            if (event.getEventType() == EventType.DOWN) {
                DownData down = eventListener.onDownEvent(event);
                guiController.getRenderer().refreshBrick(down.getViewData());
            } else if (event.getEventType() == EventType.HOLD) {
                ViewData data = eventListener.onHoldBrickEvent(event);
                guiController.getRenderer().refreshBrick(data);
            } else {
                guiController.handleMoveEvent(event);
            }
        }
        keyEvent.consume();
    }
}
