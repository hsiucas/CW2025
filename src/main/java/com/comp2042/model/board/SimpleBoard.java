package com.comp2042.model.board;

import com.comp2042.logic.board.MatrixOperations;
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
import java.util.Arrays;
import java.util.List;

public class SimpleBoard implements Board {

    private static final int X_SPAWN_OFFSET = 3;
    private static final int Y_SPAWN_OFFSET = 2;
    private static final int Y_OFFSET_IN_GARBAGE_ROW = -1;

    private final int height;
    private final int width;
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
    private final Level level;
    private final BrickHolder brickHolder;

    public SimpleBoard(int height, int width, BrickGenerator brickGenerator) {
        this.height = height;
        this.width = width;
        currentGameMatrix = new int[height][width];
        this.brickGenerator = brickGenerator;
        rotationState = new RotationState();
        currentOffset = new Point(X_SPAWN_OFFSET, Y_SPAWN_OFFSET);
        score = new Score();
        lines = new Lines();
        collisionDetector = new CollisionDetector();
        landingHandler = new BrickLandingHandler();
        brickMover = new BrickMover();
        brickRotator = new BrickRotator();
        brickSpawnHandler = new BrickSpawnHandler();
        level = new Level();
        this.brickHolder = new BrickHolder();
    }

    @Override
    public boolean createNewBrick() {
        brickHolder.resetHold();
        Brick currentBrick = brickGenerator.getBrick();
        rotationState.setBrick(currentBrick);
        int[][] shape = rotationState.getCurrentShape();

        Point spawn = brickSpawnHandler.getGameboySpawnPoint(currentGameMatrix, width, shape);

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
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        List<int[][]> nextBricks = ((RandomBrickGenerator) brickGenerator).getNextThreeBricks();
        return new ViewData(rotationState.getCurrentShape(),
                            currentOffset.y,
                            currentOffset.x,
                            nextBricks,
                            brickHolder.getHeldBrickMatrix());
    }

    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = landingHandler.mergeBrick(currentGameMatrix, rotationState.getCurrentShape(), currentOffset);
    }

    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = landingHandler.handleClearRows(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        if (clearRow.getLinesRemoved() > 0) {
            handleLevelProgression(clearRow.getLinesRemoved());
        }
        return clearRow;
    }

    private void handleLevelProgression(int linesRemoved) {
        lines.add(linesRemoved);
        if (rules.shouldLevelUp(lines.getLines(), level.getLevel())) {
            level.increment();
        }
    }

    @Override
    public Score getScore() {
        return score;
    }

    public Lines getLines() {
        return lines;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public void newGame() {
        currentGameMatrix = new int[height][width];
        score.reset();
        lines.reset();
        createNewBrick();
    }

    public void setRules(GameModeRules rules) {
        this.rules = rules;
        level.levelProperty().set(rules.getInitialLevel());
    }

    public boolean addGarbageRow(boolean isIndestructible) {
        if (MatrixOperations.isGameOver(currentGameMatrix)) {
            return true;
        }

        this.currentGameMatrix = MatrixOperations.addGarbageRow(currentGameMatrix, isIndestructible);

        if (currentOffset.y > 0) {
            currentOffset.translate(0, Y_OFFSET_IN_GARBAGE_ROW);
        }

        return MatrixOperations.isGameOver(currentGameMatrix);
    }

    public void rowToValue(int row, int value) {
        if (row >= 0 && row < height) {
            Arrays.fill(currentGameMatrix[row], value);
        }
    }

    public void holdBrick() {
        boolean swapped = brickHolder.holdBrick(rotationState, brickGenerator);
        if (swapped) currentOffset = brickSpawnHandler.getGameboySpawnPoint(currentGameMatrix, width, rotationState.getCurrentShape());
    }

    public void hardDrop() {
        int rowsFallen = brickMover.hardDrop(
                currentGameMatrix,
                rotationState.getCurrentShape(),
                currentOffset,
                collisionDetector
        );

        if (rowsFallen > 0) {
            score.add(rowsFallen * 2);
        }
    }
}
