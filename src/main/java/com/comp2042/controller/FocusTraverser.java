package com.comp2042.controller;

import com.comp2042.application.SoundManager;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

        root.addEventFilter(KeyEvent.ANY, event -> {
            KeyCode code = event.getCode();
            KeyCode mapped = null;

            switch (code) {
                case W:
                case A:
                case LEFT:
                    mapped = KeyCode.UP;
                    break;
                case S:
                case D:
                case RIGHT:
                    mapped = KeyCode.DOWN;
                    break;
                case ENTER:
                    mapped = KeyCode.SPACE;
                    break;
                default:
                    return;
            }
            if (mapped != null) {
                event.consume();
                KeyEvent fakeEvent = new KeyEvent(
                        event.getEventType(),
                        "", "",
                        mapped,
                        event.isShiftDown(),
                        event.isControlDown(),
                        event.isAltDown(),
                        event.isMetaDown()
                );

                EventTarget eventTarget = event.getTarget();
                if (eventTarget instanceof Node) {
                    ((Node) eventTarget).fireEvent(fakeEvent);
                }
            }
        });
    }
}
