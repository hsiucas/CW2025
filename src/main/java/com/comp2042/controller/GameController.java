package com.comp2042.controller;

import com.comp2042.logic.rules.SurvivalModeRules;
import com.comp2042.model.bricks.tetromino.RandomBrickGenerator;
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
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.util.Duration;

public class GameController implements InputEventListener {

    private final Board board = new SimpleBoard(GameConfiguration.BOARD_HEIGHT, GameConfiguration.BOARD_WIDTH, new RandomBrickGenerator());
    private final GuiController viewGuiController;
    private final GameRenderer gameRenderer;
    private final GameModeRules gameModeRules;
    private double currentSpeed;
    private Timeline survivalTimer;

    public GameController(GuiController c, GameModeRules rules) {
        this.viewGuiController = c;
        this.gameRenderer = c.getRenderer();
        this.gameModeRules = rules;
        ((SimpleBoard) board).setRules(rules);
        this.currentSpeed = rules.getInitialSpeedDelay();
        if (rules instanceof SurvivalModeRules) {
            initialiseSurvivalMechanics();
        }
        board.createNewBrick();
    }

    @Override
    public DownData onTick() {
        boolean moved = board.moveBrickDown();

        if (moved) {
            return new DownData(null, board.getViewData(), false);
        }

        board.mergeBrickToBackground();
        ClearRow clear = board.clearRows();

        if (clear.getLinesRemoved() > 0)
            board.getScore().add(clear.getScoreBonus());

        boolean gameOver = !board.createNewBrick();

        if(gameOver) {
            stopGame();
        }

        checkSpeed();
        return new DownData(clear, board.getViewData(), gameOver);
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean moved = board.moveBrickDown();

        if (moved) {
            if (event.getEventSource() == EventSource.USER)
                board.getScore().add(1);

            return new DownData(null, board.getViewData(), false);
        }

        board.mergeBrickToBackground();
        ClearRow clear = board.clearRows();

        if (clear.getLinesRemoved() > 0)
            board.getScore().add(clear.getScoreBonus());

        boolean isGameOver = !board.createNewBrick();
        gameRenderer.refreshGameBackground(board.getBoardMatrix());

        if(isGameOver) {
            stopGame();
        }

        checkSpeed();
        return new DownData(clear, board.getViewData(), isGameOver);
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateBrickCounterClockwise();
        return board.getViewData();
    }

    @Override
    public ViewData onHoldBrickEvent(MoveEvent event) {
        if (!gameModeRules.isHoldBrickAllowed()) return board.getViewData();
        ((SimpleBoard) board).holdBrick();
        return board.getViewData();
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
        return ((SimpleBoard) board).getLines().linesProperty();
    }

    public IntegerProperty levelProperty() {
        return ((SimpleBoard) board).getLevel().levelProperty();
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
        ((SimpleBoard) board).rowToValue(row, 0);
    }
}
