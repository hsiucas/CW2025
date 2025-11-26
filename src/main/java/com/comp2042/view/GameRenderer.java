package com.comp2042.view;

import com.comp2042.logic.board.ViewData;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GameRenderer {
    private static final int BRICK_SIZE = 20;
    private static final double Y_LAYOUT_ADJUSTMENT = -42;
    private static final int GAMEBOY_SPAWN = 2;

    private GridPane gamePanel;
    private GridPane brickPanel;
    private GridPane nextBrick;

    private Rectangle[][] displayMatrix;
    private Rectangle[][] rectangles;

    public GameRenderer(GridPane gamePanel, GridPane brickPanel, GridPane nextBrick) {
        this.gamePanel = gamePanel;
        this.brickPanel = brickPanel;
        this.nextBrick = nextBrick;
    }

    public void initBackground(int[][] boardMatrix) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        gamePanel.getChildren().clear();
        for (int row = 2; row < boardMatrix.length; row++)
            for (int col = 0; col < boardMatrix[row].length; col++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE, getFillColor(boardMatrix[row][col]));
                displayMatrix[row][col] = rectangle;
                gamePanel.add(rectangle, col, row - GAMEBOY_SPAWN);
            }
    }

    public void initBrick(ViewData brick) {
        brickPanel.getChildren().clear();
        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];

        for (int row = 0; row < brick.getBrickData().length; row++)
            for (int col = 0; col < brick.getBrickData()[row].length; col++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE, getFillColor(brick.getBrickData()[row][col]));
                rectangles[row][col] = rectangle;
                brickPanel.add(rectangle, col, row);
            }
    }

    public void refreshGameBackground(int[][] board) {
        for (int row = 2; row < board.length; row++)
            for (int col = 0; col < board[row].length; col++)
                displayMatrix[row][col].setFill(getFillColor(board[row][col]));
    }

    public void refreshBrick(ViewData brick) {
//        if (isPause.getValue()) return;

        double xPos = gamePanel.getLayoutX() + brick.getxPosition() * (BRICK_SIZE + brickPanel.getHgap());
        double yPos = Y_LAYOUT_ADJUSTMENT + gamePanel.getLayoutY() + brick.getyPosition() * (BRICK_SIZE + brickPanel.getVgap());

        brickPanel.setLayoutX(xPos);
        brickPanel.setLayoutY(yPos);

        if (rectangles.length != brick.getBrickData().length || rectangles[0].length != brick.getBrickData()[0].length) {
            initBrick(brick);
        }

        for (int row = 0; row < brick.getBrickData().length; row++)
            for (int col = 0; col < brick.getBrickData()[row].length; col++)
                rectangles[row][col].setFill(getFillColor(brick.getBrickData()[row][col]));

        previewPanel(brick.getNextBrickData());
    }

    public void previewPanel(int[][] nextBrickData){
        nextBrick.getChildren().clear();
        for (int row = 0; row < nextBrickData.length; row++)
            for (int col = 0; col < nextBrickData[row].length; col++)
                if (nextBrickData[row][col] != 0)
                    nextBrick.add(new Rectangle(BRICK_SIZE, BRICK_SIZE, getFillColor(nextBrickData[row][col])), col, row);
    }

    private Paint getFillColor(int i) {
        return switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOWGREEN;
            case 5 -> Color.RED;
            case 6 -> Color.FIREBRICK;
            case 7 -> Color.BURLYWOOD;
            default -> Color.WHITE;
        };
    }
}
