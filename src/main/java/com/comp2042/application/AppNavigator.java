package com.comp2042.application;

import com.comp2042.controller.GameConfiguration;
import com.comp2042.controller.GameController;
import com.comp2042.controller.GameModeController;
import com.comp2042.controller.MainMenuController;
import com.comp2042.controller.StartScreenController;
import com.comp2042.logic.rules.*;
import com.comp2042.model.board.*;
import com.comp2042.model.bricks.tetromino.RandomBrickGenerator;
import com.comp2042.view.GuiController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class AppNavigator {
    private final Stage stage;

    public AppNavigator(Stage stage) {
        this.stage = stage;
        this.stage.setResizable(false);
        this.stage.sizeToScene();
    }

    public void toStartScreen() {
        loadScene("startScreen.fxml");
    }

    public void toMainMenu() {
        loadScene("mainMenu.fxml");
    }

    public void toGameModeSelection() {
        loadScene("gameSelectionLayout.fxml");
    }

    public void toInstructionsScreen() {
        loadScene("instructionsLayout.fxml");
    }

    private void loadScene(String fxmlPath) {
        try {
            URL location = getClass().getClassLoader().getResource(fxmlPath);
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            Parent root = fxmlLoader.load();

            Object controller = fxmlLoader.getController();

            if (controller instanceof StartScreenController) {
                ((StartScreenController) controller).setNavigator(this);
            } else if (controller instanceof MainMenuController) {
                ((MainMenuController) controller).setNavigator(this);
            } else if (controller instanceof GameModeController) {
                ((GameModeController) controller).setNavigator(this);
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            root.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

            Scene scene = new Scene(root);
            stage.setScene(scene);

            Board board;
            if (mode == GameMode.FOUR_WAY) {
                board = new FourWayBoard(GameConfiguration.FOUR_WAY_BOARD_LENGTH, new RandomBrickGenerator());
            } else {
                board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
            }

            GameController gameController = new GameController(controller, rules, board);
            controller.setGameMode(mode);
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

    public enum GameMode {
        CLASSIC, ZEN, SURVIVAL, FOUR_WAY
    }

    public GameModeRules createRules(GameMode mode) {
        return switch (mode) {
            case CLASSIC    -> new ClassicModeRules();
            case ZEN        -> new ZenModeRules();
            case SURVIVAL   -> new SurvivalModeRules();
            case FOUR_WAY   -> new FourWayRules();
        };
    }
}
