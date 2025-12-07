package com.comp2042.view;

import com.comp2042.application.AppNavigator;
import com.comp2042.application.SoundManager;
import com.comp2042.controller.GameController;
import com.comp2042.controller.KeyInputHandler;
import com.comp2042.model.events.InputEventListener;
import com.comp2042.model.events.MoveEvent;
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

public class GuiController implements Initializable, GameLoopListener {

    private static final int FONT_SIZE = 38;

    @FXML private GridPane gamePanel;
    @FXML private GridPane brickPanel;
    @FXML private GridPane nextBrick;
    @FXML private GridPane nextBrick2;
    @FXML private GridPane nextBrick3;
    @FXML private GridPane holdBrick;
    @FXML private Label pauseLabel;
    @FXML private Text score;
    @FXML private Text lines;
    @FXML private Text level;

    private GameLooper gameLooper;
    private GameRenderer gameRenderer;
    private InputEventListener eventListener;
    private AppNavigator appNavigator;
    private AppNavigator.GameMode currentGameMode;

    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("fonts/PressStart2P.ttf").toExternalForm(), FONT_SIZE);
        gamePanel.setFocusTraversable(true);
        brickPanel.setManaged(false);

        isPause.addListener((obs,  oldValue, newValue) -> {
            if (gameLooper != null) {
                if (newValue) {
                    gameLooper.pause();
                    pauseLabel.setVisible(true);
                    SoundManager.getInstance().playSelectSound();
                    SoundManager.getInstance().pauseBGM();
                } else {
                    gameLooper.resume();
                    pauseLabel.setVisible(false);
                    SoundManager.getInstance().resumeBGM();
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
            case LEFT   -> eventListener.onLeftEvent(event);
            case RIGHT  -> eventListener.onRightEvent(event);
            case ROTATE -> eventListener.onRotateEvent(event);
            case UP     -> eventListener.onUpEvent(event);
            default     -> null;
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
        if (level != null) {
            level.textProperty().bind(levelProperty.asString());
        }
    }

    @Override
    public void onGameOver() {
        gameOver();
    }

    public void gameOver() {
        if (gameLooper != null) gameLooper.stop();
        isGameOver.set(true);
        GameOverAnimator animator = new GameOverAnimator(
                gameRenderer,
                (GameController) eventListener,
                appNavigator,
                gameLooper
        );
        animator.play();
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

    public void setAppNavigator(AppNavigator appNavigator) {
        this.appNavigator = appNavigator;
    }

    public void setGameMode(AppNavigator.GameMode mode) {
        this.currentGameMode = mode;

        BoardRenderConfiguration configuration;
        if (currentGameMode == AppNavigator.GameMode.FOUR_WAY) {
            configuration = BoardRenderConfiguration.fourWay();
        } else {
            configuration = BoardRenderConfiguration.standard();
        }

        this.gameRenderer = new GameRenderer(gamePanel, brickPanel, nextBrick, nextBrick2, nextBrick3, holdBrick, configuration);

    }

    public AppNavigator.GameMode getCurrentGameMode() {
        return currentGameMode;
    }

    public void returnToMenu() {
        if (gameLooper != null) gameLooper.stop();
        if (eventListener instanceof GameController) {
            ((GameController) eventListener).stopGame();
        }
        if (appNavigator != null) {
            appNavigator.toGameModeSelection();
        }
    }
}