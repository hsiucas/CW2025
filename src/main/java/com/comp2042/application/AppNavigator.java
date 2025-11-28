package com.comp2042.application;

import com.comp2042.controller.GameController;
import com.comp2042.controller.StartScreenController;
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

    private void loadScene(String fxmlPath) {
        try {
            URL location = getClass().getClassLoader().getResource(fxmlPath);
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            Parent root = fxmlLoader.load();

            Object controller = fxmlLoader.getController();
            if (controller instanceof StartScreenController) {
                ((StartScreenController) controller).setNavigator(this);
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            root.requestFocus();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGameScene() {
        try {
            URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            Parent root = fxmlLoader.load();
            GuiController controller = fxmlLoader.getController();

            Scene scene = new Scene(root);
            stage.setScene(scene);

            GameController gameController = new GameController(controller);
            controller.setEventListener(gameController);
            controller.bindScore(gameController.scoreProperty());
            controller.bindLines(gameController.linesProperty());
            controller.initGameView(gameController.getBoardMatrix(), gameController.getViewData());
            controller.startGameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
