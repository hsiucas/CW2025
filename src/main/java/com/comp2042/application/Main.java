package com.comp2042.application;

import com.comp2042.controller.GameController;
import com.comp2042.view.GuiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = fxmlLoader.load();
        GuiController c = fxmlLoader.getController();

        primaryStage.setTitle("TetrisJFX");
//        Scene scene = new Scene(root, 419,377);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        primaryStage.setResizable(false);

        GameController gameController = new GameController(c);
        c.setEventListener(gameController);
        c.bindScore(gameController.scoreProperty());
        c.bindLines(gameController.linesProperty());
        c.initGameView(gameController.getBoardMatrix(), gameController.getViewData());
        c.startGameLoop();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
