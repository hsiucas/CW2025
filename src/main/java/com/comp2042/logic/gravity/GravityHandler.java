package com.comp2042.logic.gravity;

import com.comp2042.board.SimpleBoard;
import com.comp2042.logic.board.ViewData;

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
