package com.comp2042.board;

import com.comp2042.logic.board.ViewData;
import com.comp2042.logic.collision.ClearRow;
import com.comp2042.logic.scoring.Score;

public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateBrickCounterClockwise();

    boolean createNewBrick();

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBrickToBackground();

    ClearRow clearRows();

    Score getScore();

    void newGame();
}
