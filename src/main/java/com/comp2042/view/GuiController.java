package com.comp2042.view;

import com.comp2042.controller.GameController;
import com.comp2042.model.events.EventSource;
import com.comp2042.model.events.EventType;
import com.comp2042.model.events.InputEventListener;
import com.comp2042.model.events.MoveEvent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
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
    @FXML private Group groupNotification;
    @FXML private Text score;
    @FXML private ToggleButton pauseButton;

    private GameLooper gameLooper;
    private GameRenderer gameRenderer;
    private InputEventListener eventListener;

    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.gameRenderer = new GameRenderer(gamePanel, brickPanel, nextBrick);

        Font.loadFont(getClass().getClassLoader().getResource("fonts/PressStart2P.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        groupNotification.setVisible(false);
        groupNotification.setManaged(false);
        brickPanel.setManaged(false);

        pauseButton.selectedProperty().bindBidirectional(isPause);
        pauseButton.selectedProperty().addListener((obs,  oldValue, newValue) -> {
            if (gameLooper != null) {
                if (newValue) { gameLooper.pause(); }
                else gameLooper.resume();
            }
            pauseButton.setText(newValue ? "Resume" : "Pause");
        });

        inputHandling();
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
    }

    public void startGameLoop() {
        this.gameLooper = new GameLooper(this, eventListener);
        gameLooper.start();
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        gameRenderer.initBackground(boardMatrix);
        gameRenderer.initBrick(brick);
        gameRenderer.previewPanel(brick.getNextBrickData());
        gameRenderer.refreshBrick(brick);
    }

    public void inputHandling() {
        gamePanel.setOnKeyPressed(keyEvent -> {
            if (isPause.get() || isGameOver.get()) { return; }

            MoveEvent event = null;

            if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A)
                event = new MoveEvent(EventType.LEFT, EventSource.USER);
            else if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D)
                event = new MoveEvent(EventType.RIGHT, EventSource.USER);
            else if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W)
                event = new MoveEvent(EventType.ROTATE, EventSource.USER);
            else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S)
                event = new MoveEvent(EventType.DOWN, EventSource.USER);
            else if (keyEvent.getCode() == KeyCode.N)
                eventListener.createNewGame();
            else if (keyEvent.getCode() == KeyCode.ESCAPE)
                pauseButton.setSelected(!pauseButton.isSelected());

            if (event != null) {
                if (event.getEventType() == EventType.DOWN) {
                    DownData down = eventListener.onDownEvent(event);
                    gameRenderer.refreshBrick(down.getViewData());
                } else {
                    handleMoveEvent(event);
                }
            }

            keyEvent.consume();
        });
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