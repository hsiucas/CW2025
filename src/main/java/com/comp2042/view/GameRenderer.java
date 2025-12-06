package com.comp2042.view;

import com.comp2042.logic.board.ViewData;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static com.comp2042.model.bricks.core.BrickColourFactory.getFillColor;

public class GameRenderer {
    private static final int BRICK_SIZE = 20;
    private static final int SMALL_BRICK_SIZE = 19;
    private static final int SMALLER_BRICK_SIZE = 7;
    private static final int HOLD_BRICK_SIZE = 13;
    private static final double X_LAYOUT_ADJUSTMENT = 40;
    private static final double Y_LAYOUT_ADJUSTMENT = -42;
    private static final int GAME_BOY_SPAWN = 2;
    private static final double GHOST_BRICK_OPACITY = 0.1;

    private GridPane gamePanel;
    private GridPane brickPanel;
    private GridPane nextBrick;
    private GridPane nextBrick2;
    private GridPane nextBrick3;
    private GridPane holdBrick;

    private Rectangle[][] displayMatrix;
    private Rectangle[][] rectangles;
    private final List<Rectangle> ghostBricks = new ArrayList<>();

    public GameRenderer(GridPane gamePanel, GridPane brickPanel, GridPane nextBrick, GridPane nextBrick2, GridPane nextBrick3, GridPane holdBrick) {
        this.gamePanel = gamePanel;
        this.brickPanel = brickPanel;
        this.nextBrick = nextBrick;
        this.nextBrick2 = nextBrick2;
        this.nextBrick3 = nextBrick3;
        this.holdBrick = holdBrick;
    }

    public void initBackground(int[][] boardMatrix) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        gamePanel.getChildren().clear();
        for (int row = GAME_BOY_SPAWN; row < boardMatrix.length; row++)
            for (int col = 0; col < boardMatrix[row].length; col++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE, getFillColor(boardMatrix[row][col]));
                displayMatrix[row][col] = rectangle;
                gamePanel.add(rectangle, col, row - GAME_BOY_SPAWN);
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
        for (int row = GAME_BOY_SPAWN; row < board.length; row++)
            for (int col = 0; col < board[row].length; col++)
                displayMatrix[row][col].setFill(getFillColor(board[row][col]));
    }

    public void refreshBrick(ViewData brick) {
//        double xPos = X_LAYOUT_ADJUSTMENT + brick.getxPosition() * (BRICK_SIZE + brickPanel.getHgap());
//        double yPos = Y_LAYOUT_ADJUSTMENT + gamePanel.getLayoutY() + brick.getyPosition() * (BRICK_SIZE + brickPanel.getVgap());

        double xPos = brick.getxPosition() * (BRICK_SIZE + brickPanel.getHgap());
        double yPos = gamePanel.getLayoutY() + brick.getyPosition() * (BRICK_SIZE + brickPanel.getVgap());


        brickPanel.setLayoutX(xPos);
        brickPanel.setLayoutY(yPos);

        if (rectangles.length != brick.getBrickData().length || rectangles[0].length != brick.getBrickData()[0].length) {
            initBrick(brick);
        }

        drawGhostBrick(brick);

        for (int row = 0; row < brick.getBrickData().length; row++)
            for (int col = 0; col < brick.getBrickData()[row].length; col++)
                rectangles[row][col].setFill(getFillColor(brick.getBrickData()[row][col]));

        previewPanel(brick.getNextBricksData());
        renderHoldBrick(brick.getHeldBrickData());
    }

    private void drawGhostBrick(ViewData brick) {
        if (!ghostBricks.isEmpty()) {
            brickPanel.getChildren().removeAll(ghostBricks);
            ghostBricks.clear();
        }

        int[][] shape = brick.getBrickData();
        int ghostY = brick.getGhostYPosition();
        int realY = brick.getyPosition();

        double dropDistance = (ghostY - realY) * (BRICK_SIZE + brickPanel.getVgap());

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != 0) {
                    Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE, getFillColor(shape[row][col]));
                    rectangle.setOpacity(GHOST_BRICK_OPACITY);
                    rectangle.setTranslateY(dropDistance);
                    brickPanel.add(rectangle, col, row);
                    ghostBricks.add(rectangle);
                }
            }
        }
    }

    public void previewPanel(List<int[][]> nextBrickData){
        nextBrick.getChildren().clear();
        nextBrick2.getChildren().clear();
        nextBrick3.getChildren().clear();
        if (nextBrickData.size() > 0) {
            singlePreview(nextBrick, nextBrickData.getFirst(), SMALL_BRICK_SIZE);
        }
        if (nextBrickData.size() > 1) {
            singlePreview(nextBrick2, nextBrickData.get(1), SMALLER_BRICK_SIZE);
        }
        if (nextBrickData.size() > 2) {
            singlePreview(nextBrick3, nextBrickData.get(2), SMALLER_BRICK_SIZE);
        }
    }

    public void singlePreview(GridPane targetGrid, int[][] brick, int brickSize) {
        targetGrid.getChildren().clear();
        for (int row = 0; row < brick.length; row++)
            for (int col = 0; col < brick[row].length; col++)
                if (brick[row][col] != 0)
                    targetGrid.add(new Rectangle(brickSize, brickSize, getFillColor(brick[row][col])), col, row);

    }

    public void renderHoldBrick(int[][] heldBrickData) {
        if (holdBrick != null) {
            holdBrick.getChildren().clear();
        }
        if (heldBrickData == null) {
            return;
        }
        singlePreview(holdBrick, heldBrickData, HOLD_BRICK_SIZE);
    }
}
