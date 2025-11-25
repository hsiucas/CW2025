package com.comp2042.board;

import com.comp2042.bricks.Brick;
import com.comp2042.bricks.BrickGenerator;
import com.comp2042.bricks.RandomBrickGenerator;
import com.comp2042.logic.*;

import java.awt.*;

public class SimpleBoard implements Board {

    private final int height;
    private final int width;
    private int[][] currentGameMatrix;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private Point currentOffset;
    private final Score score;
    private final CollisionDetector collisionDetector;
    private final BrickLandingHandler landingHandler;
    private final BrickMoverHandler brickMover;

    public SimpleBoard(int height, int width) {
        this.height = height;
        this.width = width;
        currentGameMatrix = new int[height][width];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        currentOffset = new Point(3, 2);
        score = new Score();
        collisionDetector = new CollisionDetector();
        landingHandler = new BrickLandingHandler();
        brickMover = new BrickMoverHandler();
    }

    @Override
    public boolean moveBrickDown() {
        return brickMover.moveDown(currentGameMatrix, brickRotator.getCurrentShape(), currentOffset, collisionDetector);
    }

    @Override
    public boolean moveBrickLeft() {
        return brickMover.moveLeft(currentGameMatrix, brickRotator.getCurrentShape(), currentOffset, collisionDetector);
    }

    @Override
    public boolean moveBrickRight() {
        return brickMover.moveRight(currentGameMatrix, brickRotator.getCurrentShape(), currentOffset, collisionDetector);
    }

    @Override
    public boolean rotateLeftBrick() {
        int[][] nextShape = brickRotator.getNextShape().getShape();
        if (collisionDetector.canRotate(currentGameMatrix, nextShape, currentOffset)) {
            brickRotator.setCurrentShape(brickRotator.getNextShape().getPosition());
            return true;
        }
        return false;
    }

    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(3,2);
        return landingHandler.createNewBrick(currentGameMatrix, brickRotator.getCurrentShape(), currentOffset);
    }

    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), currentOffset.y, currentOffset.x, brickGenerator.getNextBrick().getShapeMatrix().get(0));
    }

    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = landingHandler.mergeBrick(currentGameMatrix, brickRotator.getCurrentShape(), currentOffset);
    }

    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = landingHandler.handleClearRows(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public void newGame() {
        currentGameMatrix = new int[height][width];
        score.reset();
        createNewBrick();
    }
}
