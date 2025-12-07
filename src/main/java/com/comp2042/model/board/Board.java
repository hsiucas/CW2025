package com.comp2042.model.board;

import com.comp2042.logic.board.ViewData;
import com.comp2042.logic.collision.ClearRow;
import com.comp2042.logic.rules.GameModeRules;
import com.comp2042.logic.scoring.Level;
import com.comp2042.logic.scoring.Lines;
import com.comp2042.logic.scoring.Score;

public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    default boolean moveBrickUp() {
        return false;
    }

    boolean rotateBrickCounterClockwise();

    boolean createNewBrick();

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBrickToBackground();

    ClearRow clearRows();

    Score getScore();

    Lines getLines();

    Level getLevel();

    void setRules(GameModeRules rules);

    boolean holdBrick();

    void newGame();

    void rowToValue(int row, int value);
}
