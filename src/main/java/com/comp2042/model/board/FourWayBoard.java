package com.comp2042.model.board;

import com.comp2042.logic.collision.FourWayClearHandler;
import com.comp2042.logic.rules.GameModeRules;
import com.comp2042.logic.scoring.Level;
import com.comp2042.logic.scoring.Lines;
import com.comp2042.model.bricks.core.Brick;
import com.comp2042.model.bricks.core.BrickGenerator;
import com.comp2042.model.bricks.tetromino.RandomBrickGenerator;
import com.comp2042.logic.brick.*;
import com.comp2042.logic.collision.BrickLandingHandler;
import com.comp2042.logic.collision.ClearRow;
import com.comp2042.logic.collision.CollisionDetector;
import com.comp2042.logic.scoring.Score;
import com.comp2042.logic.board.ViewData;

import java.awt.*;
import java.util.List;

/**
 * Represents the board for the 4-Way game mode.
 * In this mode, bricks spawn in the center and can move in all four directions.
 * Line clearing works differently, clearing towards the center.
 */
public class FourWayBoard implements Board {
    private final int size;
    private int[][] currentGameMatrix;
    private final BrickGenerator brickGenerator;
    private final RotationState rotationState;
    private Point currentOffset;
    private final Score score;
    private final Lines lines;
    private final CollisionDetector collisionDetector;
    private final BrickLandingHandler landingHandler;
    private final BrickMover brickMover;
    private final BrickRotator brickRotator;
    private final BrickSpawnHandler brickSpawnHandler;
    private GameModeRules rules;
    private final BrickHolder brickHolder;
    private final FourWayClearHandler fourWayClearHandler;

    private final Level level;

    /**
     * Constructs a new FourWayBoard.
     *
     * @param size           The dimension of the square board (size x size).
     * @param brickGenerator The generator used to create new bricks.
     */
    public FourWayBoard(int size, BrickGenerator brickGenerator) {
        this.size = size;
        currentGameMatrix = new int[size][size];
        this.brickGenerator = brickGenerator;
        rotationState = new RotationState();
        score = new Score();
        lines = new Lines();
        collisionDetector = new CollisionDetector();
        landingHandler = new BrickLandingHandler();
        brickMover = new BrickMover();
        brickRotator = new BrickRotator();
        brickSpawnHandler = new BrickSpawnHandler();
        this.brickHolder = new BrickHolder();
        this.fourWayClearHandler = new FourWayClearHandler();

        level = new Level();
    }

    @Override
    public boolean createNewBrick() {
        brickHolder.resetHold();
        Brick currentBrick = brickGenerator.getBrick();
        rotationState.setBrick(currentBrick);
        int[][] shape = rotationState.getCurrentShape();

        Point spawn = brickSpawnHandler.getCenterSpawnPoint(currentGameMatrix, size, size, shape);

        if (spawn == null) { return false; }

        currentOffset = spawn;
        return true;
    }

    @Override
    public boolean moveBrickDown() {
        return brickMover.moveDown( currentGameMatrix,
                rotationState.getCurrentShape(),
                currentOffset,
                collisionDetector);
    }

    @Override
    public boolean moveBrickLeft() {
        return brickMover.moveLeft( currentGameMatrix,
                rotationState.getCurrentShape(),
                currentOffset,
                collisionDetector);
    }

    @Override
    public boolean moveBrickRight() {
        return brickMover.moveRight(currentGameMatrix,
                rotationState.getCurrentShape(),
                currentOffset,
                collisionDetector);
    }

    @Override
    public boolean rotateBrickCounterClockwise() {
        return brickRotator.rotateCounterClockwise( currentGameMatrix,
                rotationState,
                currentOffset,
                collisionDetector);
    }

    @Override
    public boolean moveBrickUp() {
        return brickMover.moveUp(currentGameMatrix,
                rotationState.getCurrentShape(),
                currentOffset,
                collisionDetector);
    }

    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        List<int[][]> nextBricks = ((RandomBrickGenerator) brickGenerator).getNextThreeBricks();

        return new ViewData(
                rotationState.getCurrentShape(),
                currentOffset.y,
                currentOffset.x,
                nextBricks,
                brickHolder.getHeldBrickMatrix(),
                currentOffset.y
        );
    }

    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = landingHandler.mergeBrick(currentGameMatrix, rotationState.getCurrentShape(), currentOffset);
    }

    /**
     * Clears completed rows or columns using the 4-Way specific logic.
     *
     * @return A {@link ClearRow} object detailing the results.
     */
    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = fourWayClearHandler.handleClearFourWay(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        if (clearRow.getLinesRemoved() > 0) {
            lines.add(clearRow.getLinesRemoved());
            score.add(clearRow.getScoreBonus());
        }
        return clearRow;
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public Lines getLines() {
        return lines;
    }

    @Override
    public void newGame() {
        currentGameMatrix = new int[size][size];
        score.reset();
        lines.reset();
        createNewBrick();
    }

    public void setRules(GameModeRules rules) {
        this.rules = rules;
    }

    public boolean holdBrick() {
        boolean swapped = brickHolder.holdBrick(rotationState, brickGenerator);
        if (swapped) currentOffset = brickSpawnHandler.getCenterSpawnPoint(currentGameMatrix, size, size, rotationState.getCurrentShape());
        return swapped;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public void rowToValue(int row, int value) {
        if (row >= 0 && row < currentGameMatrix.length) {
            for (int col = 0; col < currentGameMatrix[row].length; col++) {
                currentGameMatrix[row][col] = value;
            }
        }
    }
}
