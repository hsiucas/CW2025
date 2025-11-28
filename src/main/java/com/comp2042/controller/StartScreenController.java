package com.comp2042.controller;

import com.comp2042.application.AppNavigator;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

public class StartScreenController {

    private AppNavigator navigator;
    private boolean isLocked = true;

    public void setNavigator(AppNavigator navigator) {
        this.navigator = navigator;
    }

    @FXML
    private void handleKey(KeyEvent event) {
        if (isLocked) {
            isLocked = false;
            navigator.loadGameScene();
        }
    }
}
