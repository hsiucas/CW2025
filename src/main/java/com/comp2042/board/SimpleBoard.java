package com.comp2042.board;

import com.comp2042.bricks.Brick;
import com.comp2042.bricks.BrickGenerator;
import com.comp2042.bricks.RandomBrickGenerator;
import com.comp2042.logic.BrickRotator;
import com.comp2042.logic.ClearRow;
import com.comp2042.logic.MatrixOperations;
import com.comp2042.logic.Score;
import com.comp2042.logic.ViewData;
import com.comp2042.logic.CollisionDetector;

import java.awt.*;

public class SimpleBoard implements Board {

    private final int height;
    private final int width;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;
    private final CollisionDetector collisionDetector;

    public SimpleBoard(int height, int width) {
        this.height = height;
        this.width = width;
        currentGameMatrix = new int[height][width];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
        collisionDetector = new CollisionDetector();
    }

    @Override
    public boolean moveBrickDown() {
        if (collisionDetector.canMove(currentGameMatrix, brickRotator.getCurrentShape(), currentOffset, 1, 0)) {
            currentOffset.translate(0,1);
            return true;
        }
        return false;
    }

    @Override
    public boolean moveBrickLeft() {
        if (collisionDetector.canMove(currentGameMatrix, brickRotator.getCurrentShape(), currentOffset, 0, -1)) {
            currentOffset.translate(-1,0);
            return true;
        }
        return false;
    }

    @Override
    public boolean moveBrickRight() {
        if (collisionDetector.canMove(currentGameMatrix, brickRotator.getCurrentShape(), currentOffset, 0, 1)) {
            currentOffset.translate(1,0);
            return true;
        }
        return false;
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
        currentOffset = new Point(3, 2);
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), currentOffset.y, currentOffset.x);
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
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), currentOffset.y, currentOffset.x);
    }

    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
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
