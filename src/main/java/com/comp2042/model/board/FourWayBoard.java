package com.comp2042.model.board;

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

    private final Level level;

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
                null,
                currentOffset.y
        );
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
            lines.add(clearRow.getLinesRemoved());
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

    public void holdBrick() {
        boolean swapped = brickHolder.holdBrick(rotationState, brickGenerator);
        if (swapped) currentOffset = brickSpawnHandler.getCenterSpawnPoint(currentGameMatrix, size, size, rotationState.getCurrentShape());
    }

    @Override
    public Level getLevel() {
        return level;
    }
}
