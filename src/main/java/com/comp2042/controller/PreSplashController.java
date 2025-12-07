package com.comp2042.controller;

import com.comp2042.application.AppNavigator;
import com.comp2042.application.SoundManager;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class PreSplashController implements Initializable {

    @FXML private Pane rootPane;
    private AppNavigator navigator;
    private PauseTransition delay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        delay = new PauseTransition(Duration.seconds(8));
        delay.setOnFinished(e -> goToStartScreen());
        delay.play();
        rootPane.setFocusTraversable(true);
        Platform.runLater(() -> rootPane.requestFocus());
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        goToStartScreen();
    }

    private void goToStartScreen() {
        if (delay != null) {
            delay.stop();
        }
        if (navigator != null) {
            navigator.toStartScreen();
        }
    }

    public void setNavigator(AppNavigator navigator) {
        this.navigator = navigator;
    }
}
