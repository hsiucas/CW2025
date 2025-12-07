package com.comp2042.controller;

import com.comp2042.application.SoundManager;
import com.comp2042.logic.rules.SurvivalModeRules;
import com.comp2042.model.board.FourWayBoard;
import com.comp2042.model.events.InputEventListener;
import com.comp2042.model.board.Board;
import com.comp2042.model.board.SimpleBoard;
import com.comp2042.view.GameRenderer;
import com.comp2042.view.GuiController;
import com.comp2042.logic.collision.ClearRow;
import com.comp2042.logic.gravity.DownData;
import com.comp2042.logic.board.ViewData;
import com.comp2042.logic.rules.GameModeRules;
import com.comp2042.model.events.EventSource;
import com.comp2042.model.events.MoveEvent;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.util.Duration;

public class GameController implements InputEventListener {

    private final Board board;
    private final GuiController viewGuiController;
    private final GameRenderer gameRenderer;
    private final GameModeRules gameModeRules;
    private double currentSpeed;
    private Timeline survivalTimer;

    public GameController(GuiController c, GameModeRules rules, Board board) {
        this.viewGuiController = c;
        this.gameRenderer = c.getRenderer();
        this.gameModeRules = rules;
        this.board = board;
        if (board instanceof SimpleBoard){
            board.setRules(rules);
        }
        this.currentSpeed = rules.getInitialSpeedDelay();
        if (rules instanceof SurvivalModeRules) {
            initialiseSurvivalMechanics();
        }
        board.createNewBrick();
    }

    private DownData handleLocking() {
        SoundManager.getInstance().playLand();

        board.mergeBrickToBackground();
        ClearRow clear = board.clearRows();
        if (clear.getLinesRemoved() > 0) {
            board.getScore().add(clear.getScoreBonus());
            SoundManager.getInstance().playClear(clear.getLinesRemoved());
            SoundManager.getInstance().playGravityFall();
        }

        boolean gameOver = !board.createNewBrick();
        if(gameOver) {
            stopGame();
            SoundManager.getInstance().playGameOver();
            PauseTransition delay =  new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> {
                SoundManager.getInstance().playGameOverMusic();
            });
            delay.play();
        }

        gameRenderer.refreshGameBackground(board.getBoardMatrix());
        checkSpeed();
        return new DownData(clear, board.getViewData(), gameOver);
    }

    private DownData processMovement (boolean moved, boolean isSoftDrop, boolean isUserMovement) {
        if (moved) {
            if (isUserMovement) {
                SoundManager.getInstance().playMove();
            }
            if (isSoftDrop) {
                board.getScore().add(1);
            }
            return new DownData(null, board.getViewData(), false);
        }
        return handleLocking();
    }

    private ViewData handleDirection (boolean directionMoved) {
        if (directionMoved) {
            SoundManager.getInstance().playMove();
        }

        if (board instanceof FourWayBoard) {
            DownData result = processMovement(directionMoved, true, true);

            if (result.isGameOver()) {
                stopGame();
                viewGuiController.gameOver();
            }
            return result.getViewData();
        }
        return board.getViewData();
    }

    @Override
    public DownData onTick() {
        return processMovement(board.moveBrickDown(), false, false);
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean isUser = (event.getEventSource() == EventSource.USER);
        return processMovement(board.moveBrickDown(), isUser, isUser);
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        return handleDirection(board.moveBrickLeft());
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        return handleDirection(board.moveBrickRight());
    }

    @Override
    public ViewData onUpEvent(MoveEvent event) {
        return handleDirection(board.moveBrickUp());
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        if (board.rotateBrickCounterClockwise()) {
            SoundManager.getInstance().playRotate();
        }
        return board.getViewData();
    }

    @Override
    public ViewData onHoldBrickEvent(MoveEvent event) {
        if (!gameModeRules.isHoldBrickAllowed()) return board.getViewData();
        boolean held = board.holdBrick();

        if (held) {
            SoundManager.getInstance().playGravityFall();
        }

        return board.getViewData();
    }

    @Override
    public DownData onHardDropEvent(MoveEvent event) {
        if (!gameModeRules.isHardDropAllowed()) {
            return new DownData(null, board.getViewData(), false);
        }
        if (board instanceof SimpleBoard) {
            ((SimpleBoard) board).hardDrop();
        }
        return handleLocking();
    }

    @Override
    public void createNewGame() {
        board.newGame();
        gameRenderer.refreshGameBackground(board.getBoardMatrix());
    }

    public int[][] getBoardMatrix() {
        return board.getBoardMatrix();
    }

    public ViewData getViewData() {
        return board.getViewData();
    }

    public IntegerProperty scoreProperty() {
        return board.getScore().scoreProperty();
    }

    public IntegerProperty linesProperty() {
        return board.getLines().linesProperty();
    }

    public IntegerProperty levelProperty() {
        return board.getLevel().levelProperty();
    }

    public void checkSpeed() {
        int currentLevel = levelProperty().get();
        double newSpeed = gameModeRules.getSpeedDelay(currentLevel);
        if (newSpeed < currentSpeed) {
            currentSpeed = newSpeed;
            viewGuiController.updateSpeed(newSpeed);
        }
    }

    private void initialiseSurvivalMechanics() {
        survivalTimer = new Timeline(new KeyFrame(Duration.seconds(15), e -> {
            if (viewGuiController != null) {
                boolean died = ((SimpleBoard) board).addGarbageRow(false);
                SoundManager.getInstance().playGarbage();
                if (died) {
                    stopGame();
                    viewGuiController.gameOver();
                } else gameRenderer.refreshGameBackground(board.getBoardMatrix());
            }
        }));

        survivalTimer.setCycleCount(Timeline.INDEFINITE);
        survivalTimer.play();
    }

    public void stopGame() {
        if (survivalTimer != null) {
            survivalTimer.stop();
            survivalTimer = null;
        }
    }

    public void clearRowWhenGameOver(int row) {
        board.rowToValue(row, 0);
    }
}
