package com.comp2042.controller;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

public class FocusTraverser {
    public static void setupKeyboardOnly(Parent root, Node defaultNodeToFocus) {
        Platform.runLater(() -> {
            if (defaultNodeToFocus != null) {
                defaultNodeToFocus.requestFocus();
            }
        });
        root.addEventFilter(MouseEvent.ANY, Event::consume);
    }
}
