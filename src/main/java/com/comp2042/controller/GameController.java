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

/**
 * The central controller for the gameplay.
 * It acts as the bridge between the Model ({@link Board}) and the View ({@link GuiController}, {@link GameRenderer}).
 * It implements {@link InputEventListener} to respond to game events like ticks, user moves, and rotations.
 */

public class GameController implements InputEventListener {

    private final Board board;
    private final GuiController viewGuiController;
    private final GameRenderer gameRenderer;
    private final GameModeRules gameModeRules;
    private double currentSpeed;
    private Timeline survivalTimer;

    /**
     * Constructs a new GameController.
     * Initializes the game state, sets rules, and starts survival timers if applicable.
     *
     * @param c     The GUI controller responsible for the view.
     * @param rules The ruleset for the current game mode.
     * @param board The game board model.
     */
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

    /**
     * Handles the logic when a brick lands or locks into place.
     * It checks for cleared lines, plays sounds, updates score, and checks for game over.
     *
     * @return A {@link DownData} object containing the state after the brick has locked (e.g., cleared rows).
     */
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

            viewGuiController.gameOver();
        }

        gameRenderer.refreshGameBackground(board.getBoardMatrix());
        checkSpeed();
        return new DownData(clear, board.getViewData(), gameOver);
    }

    /**
     * Processes a movement command for the active brick.
     * If the move is valid, it updates the view. If invalid (collision), it may trigger locking.
     *
     * @param moved          True if the move was successful.
     * @param isSoftDrop     True if the movement was a soft drop (user pressed Down).
     * @param isUserMovement True if the movement was initiated by the user (plays sound).
     * @return The resulting {@link DownData}.
     */
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

    /**
     * Handles direction of movement.
     * @param directionMoved True if direction moved
     * @return
     */
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

    /**
     * Called on every game tick (gravity).
     * Moves the brick down automatically.
     *
     * @return The {@link DownData} describing the result of the tick.
     */
    @Override
    public DownData onTick() {
        return processMovement(board.moveBrickDown(), false, false);
    }

    /**
     * Called when the user presses the Move Down key.
     *
     * @param event The move event.
     * @return The updated {@link DownData} for rendering.
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean isUser = (event.getEventSource() == EventSource.USER);
        return processMovement(board.moveBrickDown(), isUser, isUser);
    }

    /**
     * Called when the user presses the Move Left key.
     *
     * @param event The move event.
     * @return The updated {@link ViewData} for rendering.
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        return handleDirection(board.moveBrickLeft());
    }

    /**
     * Called when the user presses the Move Right key.
     *
     * @param event The move event.
     * @return The updated {@link ViewData} for rendering.
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        return handleDirection(board.moveBrickRight());
    }

    /**
     * Called when the user presses the Move Up key.
     *
     * @param event The move event.
     * @return The updated {@link ViewData} for rendering.
     */
    @Override
    public ViewData onUpEvent(MoveEvent event) {
        return handleDirection(board.moveBrickUp());
    }

    /**
     * Called when the user presses the Rotate key.
     *
     * @param event The move event.
     * @return The updated {@link ViewData} for rendering.
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        if (board.rotateBrickCounterClockwise()) {
            SoundManager.getInstance().playRotate();
        }
        return board.getViewData();
    }

    /**
     * Called when the user attempts to hold the current brick.
     *
     * @param event The move event.
     * @return The updated {@link ViewData} showing the swapped brick.
     */
    @Override
    public ViewData onHoldBrickEvent(MoveEvent event) {
        if (!gameModeRules.isHoldBrickAllowed()) return board.getViewData();
        boolean held = board.holdBrick();

        if (held) {
            SoundManager.getInstance().playGravityFall();
        }

        return board.getViewData();
    }

    /**
     * Called when the user presses the Hard Drop key.
     * Instantly drops the brick to the bottom.
     *
     * @param event The move event.
     * @return The {@link DownData} resulting from the hard drop.
     */
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

    /**
     * Starts a new game by resetting the board and renderer.
     */
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

    /**
     * Checks if the game level needs to be increased based on the current rules.
     * Updates the game speed if the level changes.
     */
    public void checkSpeed() {
        int currentLevel = levelProperty().get();
        double newSpeed = gameModeRules.getSpeedDelay(currentLevel);
        if (newSpeed < currentSpeed) {
            currentSpeed = newSpeed;
            viewGuiController.updateSpeed(newSpeed);
        }
    }

    /**
     * Initializes the mechanics for Survival Mode.
     * Sets up a timeline that adds garbage rows at regular intervals.
     */
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

    /**
     * Stops the game loop and any running timers (e.g., survival mode).
     */
    public void stopGame() {
        if (survivalTimer != null) {
            survivalTimer.stop();
            survivalTimer = null;
        }
    }

    /**
     * Clears board when game is over
     * @param row Row
     */
    public void clearRowWhenGameOver(int row) {
        board.rowToValue(row, 0);
    }
}
