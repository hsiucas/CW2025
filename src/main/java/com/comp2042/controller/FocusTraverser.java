package com.comp2042.controller;

import com.comp2042.application.SoundManager;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class FocusTraverser {
    public static void setupKeyboardOnly(Parent root, Node defaultNodeToFocus) {
        Platform.runLater(() -> {
            if (defaultNodeToFocus != null) {
                defaultNodeToFocus.requestFocus();
            }
        });
        root.addEventFilter(MouseEvent.ANY, Event::consume);
        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.focusOwnerProperty().addListener((observable1, oldNode, newNode) -> {
                    if (newNode instanceof Button) {
                        SoundManager.getInstance().playSelectSound();
                    }
                });
            }
        });
    }
}
