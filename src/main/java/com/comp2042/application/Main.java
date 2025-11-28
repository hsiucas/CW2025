package com.comp2042.application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppNavigator appNavigator = new AppNavigator(primaryStage);
        appNavigator.toStartScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
