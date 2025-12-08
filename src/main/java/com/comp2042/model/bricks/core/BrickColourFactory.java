package com.comp2042.model.bricks.core;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Factory class responsible for mapping integer block IDs to JavaFX Colors.
 * Used by the view to render the board.
 */
public class BrickColourFactory {

    /**
     * Returns the color associated with a specific block integer ID.
     *
     * @param i The integer ID from the board matrix.
     * @return The corresponding {@link javafx.scene.paint.Paint} color.
     */
    public static Paint getFillColor(int i) {
        return switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;        // I-Piece
            case 2 -> Color.BLUE;        // J-Piece
            case 3 -> Color.DARKORANGE;  // L-Piece
            case 4 -> Color.GOLD;        // O-Piece
            case 5 -> Color.FORESTGREEN; // S-Piece
            case 6 -> Color.DARKVIOLET;  // T-Piece
            case 7 -> Color.RED;         // Z-Piece
            case 8 -> Color.GRAY;        // Garbage (Destructible)
            case 9 -> Color.DARKGRAY;    // Garbage (Indestructible)
            default -> Color.DEEPPINK;   // Obvious colour for debugging
        };
    }
}