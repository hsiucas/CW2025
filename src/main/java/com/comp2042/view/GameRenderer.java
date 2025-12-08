package com.comp2042.view;

import com.comp2042.logic.board.ViewData;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static com.comp2042.model.bricks.core.BrickColourFactory.getFillColor;

/**
 * Responsible for rendering the game state onto the JavaFX GridPanes.
 * Draws the background grid, the active brick, the ghost brick, and the next/hold previews.
 */
public class GameRenderer {
    private static final int BRICK_SIZE = 20;
    private static final int SMALL_BRICK_SIZE = 19;
    private static final int SMALLER_BRICK_SIZE = 7;
    private static final int HOLD_BRICK_SIZE = 13;
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
    private final BoardRenderConfiguration configuration;

    /**
     * Constructs a GameRenderer.
     *
     * @param gamePanel     The main grid for the board background.
     * @param brickPanel    The overlay grid for the active brick.
     * @param nextBrick     The grid for the primary next piece preview.
     * @param nextBrick2    The grid for the secondary preview.
     * @param nextBrick3    The grid for the tertiary preview.
     * @param holdBrick     The grid for the held piece.
     * @param configuration The configuration settings for board offsets.
     */
    public GameRenderer(GridPane gamePanel, GridPane brickPanel, GridPane nextBrick, GridPane nextBrick2, GridPane nextBrick3, GridPane holdBrick, BoardRenderConfiguration configuration) {
        this.gamePanel = gamePanel;
        this.brickPanel = brickPanel;
        this.nextBrick = nextBrick;
        this.nextBrick2 = nextBrick2;
        this.nextBrick3 = nextBrick3;
        this.holdBrick = holdBrick;
        this.configuration = configuration;
    }

    /**
     * Initializes the background grid with empty rectangles.
     *
     * @param boardMatrix The initial board state.
     */
    public void initBackground(int[][] boardMatrix) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        gamePanel.getChildren().clear();
        for (int row = configuration.getHiddenTopRows(); row < boardMatrix.length; row++)
            for (int col = 0; col < boardMatrix[row].length; col++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE, getFillColor(boardMatrix[row][col]));
                displayMatrix[row][col] = rectangle;
                gamePanel.add(rectangle, col, row - configuration.getHiddenTopRows());
            }
    }

    /**
     * Initializes the rectangles for the active brick.
     *
     * @param brick The view data containing the brick's shape.
     */
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

    /**
     * Updates the colors of the background grid based on the board matrix.
     *
     * @param board The current state of the board.
     */
    public void refreshGameBackground(int[][] board) {
        for (int row = configuration.getHiddenTopRows(); row < board.length; row++)
            for (int col = 0; col < board[row].length; col++)
                displayMatrix[row][col].setFill(getFillColor(board[row][col]));
    }

    /**
     * Updates the position and color of the active brick, ghost brick, and previews.
     *
     * @param brick The current view data.
     */
    public void refreshBrick(ViewData brick) {
        double xPos = configuration.getxOffset()
                        + brick.getxPosition()
                        * (BRICK_SIZE + brickPanel.getHgap());
        double yPos = configuration.getyOffset()
                        + gamePanel.getLayoutY()
                        + (brick.getyPosition() - configuration.getHiddenTopRows())
                        * (BRICK_SIZE + brickPanel.getVgap());

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

    /**
     * Draws the ghost brick (landing preview) at the calculated landing position.
     *
     * @param brick The view data containing ghost coordinates.
     */
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

    /**
     * Updates the "Next Piece" preview panels.
     *
     * @param nextBrickData A list of matrices for upcoming bricks.
     */
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

    /**
     * Helper to render a single brick into a target grid.
     *
     * @param targetGrid The GridPane to render into.
     * @param brick      The brick shape matrix.
     * @param brickSize  The size of the rectangles to draw.
     */
    public void singlePreview(GridPane targetGrid, int[][] brick, int brickSize) {
        targetGrid.getChildren().clear();
        for (int row = 0; row < brick.length; row++)
            for (int col = 0; col < brick[row].length; col++)
                if (brick[row][col] != 0)
                    targetGrid.add(new Rectangle(brickSize, brickSize, getFillColor(brick[row][col])), col, row);

    }

    /**
     * Updates the "Hold" panel with the currently held brick.
     *
     * @param heldBrickData The matrix of the held brick.
     */
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
