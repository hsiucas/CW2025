package com.comp2042.controller;

import com.comp2042.application.AppNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameModeController implements Initializable {

    private AppNavigator navigator;

    @FXML private VBox gameContainer;
    @FXML private Button classicButton;

    public void setNavigator(AppNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FocusTraverser.setupKeyboardOnly(gameContainer, classicButton);
    }

    @FXML
    private void handleClassicButton(ActionEvent event) {
        navigator.loadGameScene(AppNavigator.GameMode.CLASSIC);
    }

    @FXML
    private void handleZenButton(ActionEvent event) {
        navigator.loadGameScene(AppNavigator.GameMode.ZEN);
    }

    @FXML
    private void handleSurvivalButton(ActionEvent event) {
        navigator.loadGameScene(AppNavigator.GameMode.SURVIVAL);
    }

    @FXML
    private void handle4WayButton(ActionEvent event) {
        navigator.loadGameScene(AppNavigator.GameMode.FOUR_WAY);
    }

    @FXML
    private void handleReturnButton(ActionEvent event) {
        navigator.toMainMenu();
    }
}
