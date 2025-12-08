package com.comp2042.application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *  Entry point for the Tetris app.
 *  Extends {@link javafx.application.Application}, responsible for
 *  setting up the primary stage and launching the application.
*/

public class Main extends Application {

    /**
     * Starts JavaFX application.
     * Initialises {@link AppNavigator} and navigates to pre-splash screen.
     *
     * @param primaryStage The primary stage for this application onto which application scene can be set.
     * @throws Exception If any error occurs during startup.
     */

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppNavigator appNavigator = new AppNavigator(primaryStage);
        appNavigator.toPreSplash();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
