package com.comp2042.model.bricks.core;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class BrickColourFactory {
    public static Paint getFillColor(int i) {
        return switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUE;
            case 3 -> Color.DARKORANGE;
            case 4 -> Color.GOLD;
            case 5 -> Color.FORESTGREEN;
            case 6 -> Color.DARKVIOLET;
            case 7 -> Color.RED;
            case 8 -> Color.GRAY;
            case 9 -> Color.DARKGRAY;
            default -> Color.DEEPPINK;
        };
    }
}
