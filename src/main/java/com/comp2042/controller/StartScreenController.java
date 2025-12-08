package com.comp2042.controller;

import com.comp2042.application.AppNavigator;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

/**
 * Controller for the Start (Title) Screen.
 * Displays the game logo and waits for user input to proceed to the Main Menu.
 */
public class StartScreenController {

    private AppNavigator navigator;
    private boolean isLocked = true;

    /**
     * Injects the AppNavigator to allow scene switching.
     *
     * @param navigator The global application navigator.
     */
    public void setNavigator(AppNavigator navigator) {
        this.navigator = navigator;
    }

    /**
     * Handles any key press event on the title screen.
     * The first key press unlocks the screen and navigates to the Main Menu.
     *
     * @param event The key event.
     */
    @FXML
    private void handleKey(KeyEvent event) {
        if (isLocked) {
            isLocked = false;
            navigator.toMainMenu();
        }
    }
}