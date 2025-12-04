package com.comp2042.view;

import com.comp2042.controller.GameController;
import com.comp2042.controller.KeyInputHandler;
import com.comp2042.model.events.InputEventListener;
import com.comp2042.model.events.MoveEvent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import com.comp2042.logic.gravity.DownData;
import com.comp2042.logic.board.ViewData;

public class GuiController implements Initializable, GameLoopListener {

    @FXML private GridPane gamePanel;
    @FXML private GridPane brickPanel;
    @FXML private GridPane nextBrick;
    @FXML private GridPane nextBrick2;
    @FXML private GridPane nextBrick3;
    @FXML private Group groupNotification;
    @FXML private Text score;
    @FXML private Text lines;

    private GameLooper gameLooper;
    private GameRenderer gameRenderer;
    private InputEventListener eventListener;

    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.gameRenderer = new GameRenderer(gamePanel, brickPanel, nextBrick, nextBrick2, nextBrick3);

        Font.loadFont(getClass().getClassLoader().getResource("fonts/PressStart2P.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        groupNotification.setVisible(false);
        groupNotification.setManaged(false);
        brickPanel.setManaged(false);

        isPause.addListener((obs,  oldValue, newValue) -> {
            if (gameLooper != null) {
                if (newValue) {
                    gameLooper.pause();
                } else gameLooper.resume();
            }
        });
    }

    @Override
    public void onTick(DownData downData) {
        if (isPause.get() || isGameOver.get()) return;
        int[][] matrix = ((GameController) eventListener).getBoardMatrix();
        gameRenderer.refreshGameBackground(matrix);
        gameRenderer.refreshBrick(downData.getViewData());

        if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0)
            showScoreNotification(downData.getClearRow().getScoreBonus());
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

    private void showScoreNotification(int scoreBonus) {
        NotificationPanel notificationPanel = new NotificationPanel("+" + scoreBonus);
        groupNotification.getChildren().add(notificationPanel);
        notificationPanel.showScore(groupNotification.getChildren());
    }

    @Override
    public void onGameOver() {
        gameOver();
    }

    public void gameOver() {
        if (gameLooper != null) gameLooper.stop();
        groupNotification.setVisible(true);
        groupNotification.setManaged(true);
        isGameOver.set(true);
    }

    public GameRenderer getRenderer() {
        return this.gameRenderer;
    }
}