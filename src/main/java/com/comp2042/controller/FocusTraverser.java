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

/**
 * A utility class that manages focus traversal and input remapping for the application.
 * It enforces keyboard-only navigation by consuming mouse events and remapping
 * WASD keys to standard Arrow keys for consistent menu navigation.
 */
public class FocusTraverser {

    /**
     * Sets up the root node to handle keyboard-only navigation.
     * This method forces focus onto a default node, consumes all mouse events to prevent mouse interaction,
     * plays a sound effect when focus changes to a button and remaps WASD and Enter keys to Arrow keys and Space for consistent handling.
     * @param root               The root Parent node to attach event filters to.
     * @param defaultNodeToFocus The node that should receive initial focus.
     */
    public static void setupKeyboardOnly(Parent root, Node defaultNodeToFocus) {
        Platform.runLater(() -> {
            if (defaultNodeToFocus != null) {
                defaultNodeToFocus.requestFocus();
            }
        });

        // Block mouse input to simulate a console-like experience
        root.addEventFilter(MouseEvent.ANY, Event::consume);

        // Play sound when focus moves to a button
        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.focusOwnerProperty().addListener((observable1, oldNode, newNode) -> {
                    if (newNode instanceof Button) {
                        SoundManager.getInstance().playSelectSound();
                    }
                });
            }
        });

        // Remap keys (WASD -> Arrows, Enter -> Space)
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