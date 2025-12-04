package com.comp2042.model.board;

import com.comp2042.logic.rules.GameModeRules;
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

public class SimpleBoard implements Board {

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
    private int currentLevel;

    public SimpleBoard(int height, int width) {
        this.height = height;
        this.width = width;
        currentGameMatrix = new int[height][width];
        brickGenerator = new RandomBrickGenerator();
        rotationState = new RotationState();
        currentOffset = new Point(3, 2);
        score = new Score();
        lines = new Lines();
        collisionDetector = new CollisionDetector();
        landingHandler = new BrickLandingHandler();
        brickMover = new BrickMover();
        brickRotator = new BrickRotator();
        brickSpawnHandler = new BrickSpawnHandler();
        currentLevel = 1;
    }

    @Override
    public boolean createNewBrick() {
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
                            nextBricks);
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
            if (rules.shouldLevelUp(lines.getLines(), currentLevel)) {
                currentLevel++;
            }
        }
        return clearRow;
    }

    @Override
    public Score getScore() {
        return score;
    }

    public Lines getlines() {
        return lines;
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
        this.currentLevel = rules.getInitialLevel();
    }
}
