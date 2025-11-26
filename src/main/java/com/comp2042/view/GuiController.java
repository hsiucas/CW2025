package com.comp2042.view;

import com.comp2042.controller.GameController;
import com.comp2042.model.events.EventSource;
import com.comp2042.model.events.EventType;
import com.comp2042.model.events.InputEventListener;
import com.comp2042.model.events.MoveEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import com.comp2042.logic.gravity.DownData;
import com.comp2042.logic.board.ViewData;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;

    @FXML private GridPane gamePanel;
    @FXML private GridPane brickPanel;
    @FXML private GridPane nextBrick;
    @FXML private Group groupNotification;
    @FXML private Text score;
    @FXML private ToggleButton pauseButton;

    private Rectangle[][] displayMatrix;
    private Rectangle[][] rectangles;

    private Timeline timeLine;
    private InputEventListener eventListener;

    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("fonts/PressStart2P.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        groupNotification.setVisible(false);
        groupNotification.setManaged(false);
        brickPanel.setManaged(false);

        pauseButton.selectedProperty().bindBidirectional(isPause);
        pauseButton.selectedProperty().addListener((obs,  oldValue, newValue) -> {
            if (timeLine != null) {
                if (newValue) { timeLine.pause(); }
                else timeLine.play();
            }
            pauseButton.setText(newValue ? "Resume" : "Pause");
        });

        inputHandling();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void startGameLoop() {
        timeLine = new Timeline(new KeyFrame(Duration.millis(400), e -> {
            if (isPause.get() || isGameOver.get()) return;

            DownData downData = eventListener.onTick();
            int[][] matrix = ((GameController) eventListener).getBoardMatrix();
            refreshGameBackground(matrix);
            refreshBrick(downData.getViewData());

            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0)
                showScoreNotification(downData.getClearRow().getScoreBonus());

            if (downData.isGameOver()) gameOver();

        }));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        initBackground(boardMatrix);
        initBrick(brick);
        previewPanel(brick.getNextBrickData());
        refreshBrick(brick);
    }

    public void initBackground(int[][] boardMatrix) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        gamePanel.getChildren().clear();
        for (int row = 2; row < boardMatrix.length; row++)
            for (int col = 0; col < boardMatrix[row].length; col++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE, getFillColor(boardMatrix[row][col]));
                displayMatrix[row][col] = rectangle;
                gamePanel.add(rectangle, col, row - 2);
            }
    }

    public void initBrick(ViewData brick) {
        brickPanel.getChildren().clear();
        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];

        for (int row = 0; row < brick.getBrickData().length; row++)
            for (int col = 0; col < brick.getBrickData()[row].length; col++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE, getFillColor(brick.getBrickData()[row][col]));
                rectangles[row][col] = rectangle;
                brickPanel.add(rectangle, col, row);
            }
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
                    refreshBrick(down.getViewData());
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
        if (data != null) refreshBrick(data);
    }

    public void refreshGameBackground(int[][] board) {
        for (int row = 2; row < board.length; row++)
            for (int col = 0; col < board[row].length; col++)
                displayMatrix[row][col].setFill(getFillColor(board[row][col]));
    }

    private void refreshBrick(ViewData brick) {
        if (isPause.getValue()) return;

        double xPos = gamePanel.getLayoutX() + brick.getxPosition() * (BRICK_SIZE + brickPanel.getHgap());
        double yPos = -42 + gamePanel.getLayoutY() + brick.getyPosition() * (BRICK_SIZE + brickPanel.getVgap());

        brickPanel.setLayoutX(xPos);
        brickPanel.setLayoutY(yPos);

        if (rectangles.length != brick.getBrickData().length || rectangles[0].length != brick.getBrickData()[0].length) {
            initBrick(brick);
        }

        for (int row = 0; row < brick.getBrickData().length; row++)
            for (int col = 0; col < brick.getBrickData()[row].length; col++)
                rectangles[row][col].setFill(getFillColor(brick.getBrickData()[row][col]));

        previewPanel(brick.getNextBrickData());
    }

    private void previewPanel(int[][] nextBrickData){
        nextBrick.getChildren().clear();
        for (int row = 0; row < nextBrickData.length; row++)
            for (int col = 0; col < nextBrickData[row].length; col++)
                if (nextBrickData[row][col] != 0)
                    nextBrick.add(new Rectangle(BRICK_SIZE, BRICK_SIZE, getFillColor(nextBrickData[row][col])), col, row);
    }

    public void bindScore(IntegerProperty scoreProperty) {
        score.textProperty().bind(scoreProperty.asString());
    }

    private void showScoreNotification(int scoreBonus) {
        NotificationPanel notificationPanel = new NotificationPanel("+" + scoreBonus);
        groupNotification.getChildren().add(notificationPanel);
        notificationPanel.showScore(groupNotification.getChildren());
    }

    public void gameOver() {
        timeLine.stop();
        groupNotification.setVisible(true);
        groupNotification.setManaged(true);
        isGameOver.set(true);
    }

    private Paint getFillColor(int i) {
        return switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOWGREEN;
            case 5 -> Color.RED;
            case 6 -> Color.FIREBRICK;
            case 7 -> Color.BURLYWOOD;
            default -> Color.WHITE;
        };
    }
}