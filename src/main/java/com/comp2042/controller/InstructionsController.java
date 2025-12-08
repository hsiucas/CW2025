package com.comp2042.controller;

import com.comp2042.application.AppNavigator;
import com.comp2042.application.SoundManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Instructions (How-To) screen.
 * Displays control mappings and game rules.
 * Allows the user to return to the main menu by pressing any key.
 */
public class InstructionsController implements Initializable {

    @FXML private StackPane rootPane;
    private AppNavigator navigator;

    /**
     * Initializes the controller.
     * Sets up the focus on the root pane to ensure it can receive key events immediately.
     *
     * @param location  The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Ensure the screen can receive input
        FocusTraverser.setupKeyboardOnly(rootPane, rootPane);
    }

    /**
     * Handles any key press event on this screen.
     * Any key press will trigger a return to the Main Menu.
     *
     * @param event The key event.
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        returnToMenu();
    }

    /**
     * Returns the user to the Main Menu.
     * Plays a selection sound effect.
     */
    private void returnToMenu() {
        SoundManager.getInstance().playSelectSound();
        if (navigator != null) {
            navigator.toMainMenu();
        }
    }

    /**
     * Injects the AppNavigator.
     *
     * @param navigator The global application navigator.
     */
    public void setNavigator(AppNavigator navigator) {
        this.navigator = navigator;
    }
}