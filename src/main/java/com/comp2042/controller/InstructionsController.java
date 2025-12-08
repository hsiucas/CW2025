package com.comp2042.controller;

import com.comp2042.application.AppNavigator;
import com.comp2042.application.SoundManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class InstructionsController implements Initializable {

    @FXML private StackPane rootPane;
    private AppNavigator navigator;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        FocusTraverser.setupKeyboardOnly(rootPane, rootPane);
    }

    @FXML
    public void handleKeyPress(KeyEvent keyEvent) {
        returnToMenu();
    }

    private void returnToMenu() {
        SoundManager.getInstance().playSelectSound();
        if (navigator != null) navigator.toMainMenu();
    }

    public void setNavigator(AppNavigator navigator) {
        this.navigator = navigator;
    }
}
