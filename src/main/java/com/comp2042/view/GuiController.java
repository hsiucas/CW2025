package com.comp2042.view;

import com.comp2042.application.AppNavigator;
import com.comp2042.controller.GameController;
import com.comp2042.controller.KeyInputHandler;
import com.comp2042.model.events.InputEventListener;
import com.comp2042.model.events.MoveEvent;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import com.comp2042.logic.gravity.DownData;
import com.comp2042.logic.board.ViewData;
import javafx.util.Duration;

public class GuiController implements Initializable, GameLoopListener {

    @FXML private GridPane gamePanel;
    @FXML private GridPane brickPanel;
    @FXML private GridPane nextBrick;
    @FXML private GridPane nextBrick2;
    @FXML private GridPane nextBrick3;
    @FXML private Label pauseLabel;
    @FXML private Text score;
    @FXML private Text lines;
    @FXML private Text level;

    private GameLooper gameLooper;
    private GameRenderer gameRenderer;
    private InputEventListener eventListener;
    private AppNavigator navigator;

    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.gameRenderer = new GameRenderer(gamePanel, brickPanel, nextBrick, nextBrick2, nextBrick3);

        Font.loadFont(getClass().getClassLoader().getResource("fonts/PressStart2P.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        brickPanel.setManaged(false);

        isPause.addListener((obs,  oldValue, newValue) -> {
            if (gameLooper != null) {
                if (newValue) {
                    gameLooper.pause();
                    pauseLabel.setVisible(true);
                } else {
                    gameLooper.resume();
                    pauseLabel.setVisible(false);
                }
            }
        });
    }

    @Override
    public void onTick(DownData downData) {
        if (isPause.get() || isGameOver.get()) return;
        int[][] matrix = ((GameController) eventListener).getBoardMatrix();
        gameRenderer.refreshGameBackground(matrix);
        gameRenderer.refreshBrick(downData.getViewData());
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;

        KeyInputHandler handler = new KeyInputHandler(
                eventListener,
                () -> isPause.set(!isPause.get()),
                isPause,
                isGameOver,
                this
        );
        gamePanel.setOnKeyPressed(handler);
    }

    public void startGameLoop(double speed) {
        this.gameLooper = new GameLooper(this, eventListener, speed);
        gameLooper.start();
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        gameRenderer.initBackground(boardMatrix);
        gameRenderer.initBrick(brick);
        gameRenderer.previewPanel(brick.getNextBricksData());
        gameRenderer.refreshBrick(brick);
    }

    public void handleMoveEvent(MoveEvent event) {
        ViewData data = switch (event.getEventType()) {
            case LEFT -> eventListener.onLeftEvent(event);
            case RIGHT -> eventListener.onRightEvent(event);
            case ROTATE -> eventListener.onRotateEvent(event);
            default -> null;
        };
        if (data != null) gameRenderer.refreshBrick(data);
    }

    public void bindScore(IntegerProperty scoreProperty) {
        score.textProperty().bind(scoreProperty.asString());
    }

    public void bindLines(IntegerProperty linesProperty) {
        lines.textProperty().bind(linesProperty.asString());
    }

    public void bindLevel(IntegerProperty levelProperty) {
        level.textProperty().bind(levelProperty.asString());
    }

    @Override
    public void onGameOver() {
        gameOver();
    }

    public void gameOver() {
        if (gameLooper != null) gameLooper.stop();
        isGameOver.set(true);
        gameOverAnimation();
    }

    public GameRenderer getRenderer() {
        return this.gameRenderer;
    }

    public void updateSpeed(double newSpeed) {
        if (gameLooper != null) {
            gameLooper.stop();
        }
        this.gameLooper = new GameLooper(this, eventListener, newSpeed);
        gameLooper.start();
    }

    public void setNavigator(AppNavigator navigator) {
        this.navigator = navigator;
    }

    private void wipeAnimation() {
        int[][] matrix = ((GameController) eventListener).getBoardMatrix();
        int totalRows = matrix.length;

        Timeline wipeBoard = new Timeline();
        wipeBoard.setCycleCount(totalRows + 1);

        final int[] currentRow = {0};

        wipeBoard.getKeyFrames().add(new KeyFrame(Duration.millis(60), e -> {
            int row = currentRow[0];
            if (row < totalRows) {
                ((GameController) eventListener).clearRowWhenGameOver(row);
                gameRenderer.refreshGameBackground(((GameController) eventListener).getBoardMatrix());
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

    private void gameOverAnimation() {
        performDelay(1, () -> wipeAnimation());
    }
}