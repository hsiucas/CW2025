package com.comp2042.controller;

import com.comp2042.model.board.*;
import com.comp2042.model.events.InputEventListener;
import com.comp2042.model.board.Board;
import com.comp2042.model.board.SimpleBoard;
import com.comp2042.view.GuiController;
import com.comp2042.logic.collision.ClearRow;
import com.comp2042.logic.gravity.DownData;
import com.comp2042.logic.gravity.GravityHandler;
import com.comp2042.logic.board.ViewData;
import com.comp2042.model.events.EventSource;
import com.comp2042.model.events.MoveEvent;
import javafx.beans.property.IntegerProperty;

public class GameController implements InputEventListener {

    private final Board board = new SimpleBoard(20, 10);

    public GameController(GuiController c) {
        board.createNewBrick();
    }

    @Override
    public DownData onTick() {
        boolean moved = board.moveBrickDown();

        if (moved) {
            return new DownData(null, board.getViewData(), false);
        }

        board.mergeBrickToBackground();
        ClearRow clear = board.clearRows();

        if (clear.getLinesRemoved() > 0)
            board.getScore().add(clear.getScoreBonus());

        boolean gameOver = !board.createNewBrick();

        return new DownData(clear, board.getViewData(), gameOver);
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean moved = board.moveBrickDown();

        if (moved) {
            if (event.getEventSource() == EventSource.USER)
                board.getScore().add(1);

            return new DownData(null, board.getViewData(), false);
        }

        board.mergeBrickToBackground();
        ClearRow clear = board.clearRows();

        if (clear.getLinesRemoved() > 0)
            board.getScore().add(clear.getScoreBonus());

        boolean isGameOver = !board.createNewBrick();
        return new DownData(clear, board.getViewData(), isGameOver);
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateBrickCounterClockwise();
        return board.getViewData();
    }

    @Override
    public void createNewGame() {
        board.newGame();
    }

    public int[][] getBoardMatrix() {
        return board.getBoardMatrix();
    }

    public ViewData getViewData() {
        return board.getViewData();
    }

    public IntegerProperty scoreProperty() {
        return board.getScore().scoreProperty();
    }
}
