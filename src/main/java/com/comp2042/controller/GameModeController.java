package com.comp2042.controller;

import com.comp2042.application.AppNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GameModeController {
    private AppNavigator navigator;

    public void setNavigator(AppNavigator navigator) {
        this.navigator = navigator;
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
}
