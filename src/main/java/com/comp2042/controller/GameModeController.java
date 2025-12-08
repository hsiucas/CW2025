package com.comp2042.controller;

import com.comp2042.application.AppNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Game Mode Selection screen.
 * Allows the user to choose between Classic, Zen, Survival, and 4-Way modes.
 */
public class GameModeController implements Initializable {

    private AppNavigator navigator;

    @FXML private VBox gameContainer;
    @FXML private Button classicButton;

    /**
     * Injects the AppNavigator to allow scene switching.
     *
     * @param navigator The global application navigator.
     */
    public void setNavigator(AppNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FocusTraverser.setupKeyboardOnly(gameContainer, classicButton);
    }

    /**
     * Starts a new game in Classic Mode.
     *
     * @param event The action event.
     */
    @FXML
    private void handleClassicButton(ActionEvent event) {
        navigator.loadGameScene(AppNavigator.GameMode.CLASSIC);
    }

    /**
     * Starts a new game in Zen Mode.
     *
     * @param event The action event.
     */
    @FXML
    private void handleZenButton(ActionEvent event) {
        navigator.loadGameScene(AppNavigator.GameMode.ZEN);
    }

    /**
     * Starts a new game in Survival Mode.
     *
     * @param event The action event.
     */
    @FXML
    private void handleSurvivalButton(ActionEvent event) {
        navigator.loadGameScene(AppNavigator.GameMode.SURVIVAL);
    }

    /**
     * Starts a new game in 4-Way Mode.
     *
     * @param event The action event.
     */
    @FXML
    private void handle4WayButton(ActionEvent event) {
        navigator.loadGameScene(AppNavigator.GameMode.FOUR_WAY);
    }

    /**
     * Returns the user to the Main Menu.
     *
     * @param event The action event.
     */
    @FXML
    private void handleReturnButton(ActionEvent event) {
        navigator.toMainMenu();
    }
}