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

/**
 * Controller for the Main Menu screen.
 * Handles interactions with the Play, How-To, and Exit buttons.
 */
public class MainMenuController implements Initializable {

    private AppNavigator navigator;

    @FXML private VBox menuContainer;
    @FXML private Button playButton;

    /**
     * Injects the AppNavigator to allow scene switching.
     *
     * @param navigator The global application navigator.
     */
    public void setNavigator(AppNavigator navigator) {
        this.navigator = navigator;
    }

    /**
     * Initializes the controller class.
     * Sets up keyboard focus traversal for the menu buttons.
     *
     * @param location  The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FocusTraverser.setupKeyboardOnly(menuContainer, playButton);
    }

    /**
     * Handles the "Play" button action.
     * Navigates to the Game Mode selection screen.
     *
     * @param event The action event.
     */
    @FXML
    private void handlePlayButton(ActionEvent event) {
        navigator.toGameModeSelection();
    }

    /**
     * Handles the "How-To" button action.
     * Navigates to the Instructions screen.
     *
     * @param event The action event.
     */
    @FXML
    private void handleInstructionsButton(ActionEvent event) {
        navigator.toInstructionsScreen();
    }

    /**
     * Handles the "Exit" button action.
     * Closes the application.
     *
     * @param event The action event.
     */
    @FXML
    private void handleExitButton(ActionEvent event) {
        Platform.exit();
    }
}