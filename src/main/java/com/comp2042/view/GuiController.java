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

/**
 * The main controller for the Game View (FXML).
 * It manages the UI elements (grids, labels), handles game loop events,
 * and delegates user input to the logic controller.
 */
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

    /**
     * Initializes the controller.
     * Loads custom fonts and sets up pause logic listeners.
     */
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

    /**
     * Called by the game loop on every tick.
     * Updates the renderer with the latest game state.
     *
     * @param downData The data from the latest gravity tick.
     */
    @Override
    public void onTick(DownData downData) {
        if (isPause.get() || isGameOver.get()) return;
        int[][] matrix = ((GameController) eventListener).getBoardMatrix();
        gameRenderer.refreshGameBackground(matrix);
        gameRenderer.refreshBrick(downData.getViewData());
    }

    /**
     * Sets the input event listener (GameController) and initializes key handling.
     *
     * @param eventListener The listener for game events.
     */
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

    /**
     * Starts the game loop with a specified speed.
     *
     * @param speed The delay in milliseconds.
     */
    public void startGameLoop(double speed) {
        this.gameLooper = new GameLooper(this, eventListener, speed);
        gameLooper.start();
    }

    /**
     * Initializes the visual components of the game view.
     *
     * @param boardMatrix The initial board state.
     * @param brick       The initial brick state.
     */
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

    /**
     * Binds the Score Text in the UI to the underlying Score property in the model.
     * This ensures the UI updates automatically whenever the score changes.
     *
     * @param scoreProperty The observable integer property representing the score.
     */
    public void bindScore(IntegerProperty scoreProperty) {
        score.textProperty().bind(scoreProperty.asString());
    }

    /**
     * Binds the Lines Text in the UI to the underlying Lines property in the model.
     *
     * @param linesProperty The observable integer property representing cleared lines.
     */
    public void bindLines(IntegerProperty linesProperty) {
        lines.textProperty().bind(linesProperty.asString());
    }

    /**
     * Binds the Level Text in the UI to the underlying Level property in the model.
     *
     * @param levelProperty The observable integer property representing the current level.
     */
    public void bindLevel(IntegerProperty levelProperty) {
        if (level != null) {
            level.textProperty().bind(levelProperty.asString());
        }
    }

    /**
     * Triggered when the game is over.
     * Stops the loop and starts the game over animation.
     */
    @Override
    public void onGameOver() {
        gameOver();
    }

    /**
     * Initiates the Game Over sequence.
     * Stops the game loop, sets the game over state flag, and starts the visual
     * "wipe" animation before transitioning to the next screen.
     */
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

    /**
     * Gets the renderer responsible for drawing the game board.
     *
     * @return The {@link GameRenderer} instance.
     */
    public GameRenderer getRenderer() {
        return this.gameRenderer;
    }

    /**
     * Updates the speed of the game loop.
     * Stops the existing loop and starts a new one with the specified delay.
     *
     * @param newSpeed The new delay in milliseconds between game ticks.
     */
    public void updateSpeed(double newSpeed) {
        if (gameLooper != null) {
            gameLooper.stop();
        }
        this.gameLooper = new GameLooper(this, eventListener, newSpeed);
        gameLooper.start();
    }

    /**
     * Injects the AppNavigator to allow scene switching from the game view.
     *
     * @param appNavigator The global application navigator.
     */
    public void setAppNavigator(AppNavigator appNavigator) {
        this.appNavigator = appNavigator;
    }

    /**
     * Sets the current game mode and initializes the appropriate renderer configuration.
     *
     * @param mode The selected game mode.
     */
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

    /**
     * Retrieves the current game mode being played.
     * Used by input handlers to determine control schemes (e.g., 4-Way vs Classic).
     *
     * @return The current {@link AppNavigator.GameMode}.
     */
    public AppNavigator.GameMode getCurrentGameMode() {
        return currentGameMode;
    }

    /**
     * Stops the current game session and navigates back to the Game Mode Selection screen.
     * Stops the game loop and any logic timers before switching scenes.
     */
    public void returnToMenu() {
        if (gameLooper != null) gameLooper.stop();
        if (eventListener instanceof GameController) {
            ((GameController) eventListener).stopGame();
        }
        if (appNavigator != null) {
            appNavigator.toGameModeSelection();
        }
    }

    /**
     * Retrieves the current app navigator.
     * @return The current {@link AppNavigator}.
     */
    public AppNavigator getAppNavigator() {
        return appNavigator;
    }
}