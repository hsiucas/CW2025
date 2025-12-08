package com.comp2042.application;

import com.comp2042.controller.*;
import com.comp2042.logic.rules.*;
import com.comp2042.model.board.*;
import com.comp2042.model.bricks.tetromino.RandomBrickGenerator;
import com.comp2042.view.GuiController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Manages navigation between different scenes.
 * Responsible for loading FXML files, setting up controllers and switching contents of the primary stage.
 */

public class AppNavigator {
    private final Stage stage;

    /**
     * Constructs new AppNavigator with given primary stage.
     * @param stage Primary stage where scenes will be displayed.
     */

    public AppNavigator(Stage stage) {
        this.stage = stage;
        this.stage.setResizable(false);
        this.stage.sizeToScene();
    }

    /**
     * Navigates to pre-splash screen with disclaimer and intro.
     * Plays sound effect.
     */

    public void toPreSplash() {
        loadScene("preSplashScreen.fxml");
        SoundManager.getInstance().playGarbage();
    }

    /**
     * Navigates to start screen.
     * Plays title music.
     */

    public void toStartScreen() {
        loadScene("startScreen.fxml");
        SoundManager.getInstance().playTitleMusic();
    }

    /**
     * Navigates to main menu.
     * Plays title music.
     */

    public void toMainMenu() {
        loadScene("mainMenu.fxml");
        SoundManager.getInstance().playTitleMusic();
    }

    /**
     * Navigates to game mode selection screen.
     * Plays title music.
     */

    public void toGameModeSelection() {
        loadScene("gameSelectionLayout.fxml");
        SoundManager.getInstance().playTitleMusic();
    }

    /**
     * Navigates to instructions (How-to) screen.
     * Plays instructions music.
     */

    public void toInstructionsScreen() {
        loadScene("instructionsLayout.fxml");
        SoundManager.getInstance().playInstructionsMusic();
    }

    /**
     * Helper method to load generic FXML scene and set navigator on its controller.
     * @param fxmlPath Resource path to FXML file.
     */

    private void loadScene(String fxmlPath) {
        try {
            URL location = getClass().getClassLoader().getResource(fxmlPath);
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            Parent root = fxmlLoader.load();

            Object controller = fxmlLoader.getController();

            if (controller instanceof PreSplashController) {
                ((PreSplashController) controller).setNavigator(this);
            } else if (controller instanceof StartScreenController) {
                ((StartScreenController) controller).setNavigator(this);
            } else if (controller instanceof MainMenuController) {
                ((MainMenuController) controller).setNavigator(this);
            } else if (controller instanceof GameModeController) {
                ((GameModeController) controller).setNavigator(this);
            } else if (controller instanceof InstructionsController) {
                ((InstructionsController) controller).setNavigator(this);
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            root.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads main game scene based on selected game mode.
     * Sets up Board, Rules, Renderer and GameController.
     * @param mode Selected {@link GameMode}.
     */

    public void loadGameScene(GameMode mode) {
        GameModeRules rules = createRules(mode);

        if (rules == null) {
            System.err.println("Invalid game mode!");
            return;
        }

        try {
            String fxmlFileName = "gameLayout.fxml";

            if (mode == GameMode.CLASSIC) {
                fxmlFileName = "classicModeLayout.fxml";
            } else if (mode == GameMode.FOUR_WAY) {
                fxmlFileName = "4WayLayout.fxml";
            }

            URL location = getClass().getClassLoader().getResource(fxmlFileName);
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            Parent root = fxmlLoader.load();

            GuiController controller = fxmlLoader.getController();

            SoundManager.getInstance().playGameModeMusic(mode);

            controller.setGameMode(mode);
            Scene scene = new Scene(root);
            stage.setScene(scene);

            Board board;
            if (mode == GameMode.FOUR_WAY) {
                board = new FourWayBoard(GameConfiguration.FOUR_WAY_BOARD_LENGTH, new RandomBrickGenerator());
            } else {
                board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
            }

            GameController gameController = new GameController(controller, rules, board);

            double speed = rules.getInitialSpeedDelay();

            controller.setAppNavigator(this);
            controller.setEventListener(gameController);
            controller.bindScore(gameController.scoreProperty());
            controller.bindLines(gameController.linesProperty());
            controller.bindLevel(gameController.levelProperty());
            controller.initGameView(gameController.getBoardMatrix(), gameController.getViewData());
            controller.startGameLoop(speed);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Enumeration of game modes.
     */

    public enum GameMode {
        CLASSIC, ZEN, SURVIVAL, FOUR_WAY
    }

    /**
     * Factory method to create appropriate rules object for given game modes.
     * @param mode The game mode.
     * @return A {@link GameModeRules} implementation for selected mode.
     */

    public GameModeRules createRules(GameMode mode) {
        return switch (mode) {
            case CLASSIC    -> new ClassicModeRules();
            case ZEN        -> new ZenModeRules();
            case SURVIVAL   -> new SurvivalModeRules();
            case FOUR_WAY   -> new FourWayRules();
        };
    }
}
