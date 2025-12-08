package com.comp2042.controller;

import com.comp2042.application.AppNavigator;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Pre-Splash screen.
 * This screen displays a disclaimer or intro graphic and automatically transitions
 * to the main Start Screen after a delay, or immediately upon user input.
 */
public class PreSplashController implements Initializable {

    @FXML private Pane rootPane;
    private AppNavigator navigator;
    private PauseTransition delay;

    /**
     * Initializes the controller.
     * Sets up a timer to auto-advance the screen after 8 seconds and ensures
     * the root pane receives focus to capture key presses.
     *
     * @param location  The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        delay = new PauseTransition(Duration.seconds(8));
        delay.setOnFinished(e -> goToStartScreen());
        delay.play();

        rootPane.setFocusTraversable(true);
        Platform.runLater(() -> rootPane.requestFocus());
    }

    /**
     * Handles key press events.
     * Any key press cancels the timer and immediately advances to the Start Screen.
     *
     * @param event The key event.
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        goToStartScreen();
    }

    /**
     * Transitions to the Start Screen.
     * Stops any running timers before navigating to prevent double-navigation.
     */
    private void goToStartScreen() {
        if (delay != null) {
            delay.stop();
        }
        if (navigator != null) {
            navigator.toStartScreen();
        }
    }

    /**
     * Injects the AppNavigator to allow scene switching.
     *
     * @param navigator The global application navigator.
     */
    public void setNavigator(AppNavigator navigator) {
        this.navigator = navigator;
    }
}