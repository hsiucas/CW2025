package com.comp2042.logic;

import com.comp2042.board.SimpleBoard;

public class GravityHandler {

    private final SimpleBoard board;

    public GravityHandler(SimpleBoard board) {
        this.board = board;
    }

    public DownData tick() {
        boolean moved = board.moveBrickDown();
        if (!moved) {
            board.mergeBrickToBackground();
            ViewData viewData = board.getViewData();
            boolean newBrickCreated = board.createNewBrick();
            return new DownData(board.clearRows(), viewData, !newBrickCreated);
        }
        return new DownData(null, board.getViewData(), false);
    }

    public int[][] getBoardMatrix() {
        return board.getBoardMatrix();
    }
}
