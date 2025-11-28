package com.comp2042.controller;

import com.comp2042.application.AppNavigator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private AppNavigator navigator;

    @FXML private VBox menuContainer;
    @FXML private Button playButton;
    @FXML private Button instructionsButton;
    @FXML private Button exitButton;

    public void setNavigator(AppNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            playButton.requestFocus();
        });
    }

    @FXML
    private void handlePlayButton(ActionEvent event) {
        navigator.toGameModeSelection();
    }

    @FXML
    private void handleInstructionsButton(ActionEvent event) {
        navigator.toInstructionsScreen();
    }

    @FXML
    private void handleExitButton(ActionEvent event) {
        Platform.exit();
    }
}
