package com.comp2042.controller;

import com.comp2042.board.*;
import com.comp2042.events.InputEventListener;
import com.comp2042.view.GuiController;
import com.comp2042.logic.collision.ClearRow;
import com.comp2042.logic.gravity.DownData;
import com.comp2042.logic.gravity.GravityHandler;
import com.comp2042.logic.board.ViewData;
import com.comp2042.events.EventSource;
import com.comp2042.events.MoveEvent;

public class GameController implements InputEventListener {

    private final Board board = new SimpleBoard(20, 10);
    private final GuiController viewGuiController;

    public GameController(GuiController c) {
        this.viewGuiController = c;
        board.createNewBrick();
        GravityHandler gravityHandler = new GravityHandler((SimpleBoard) board);
        viewGuiController.setGravityHandler(gravityHandler);
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        boolean isGameOver = false;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();

            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }

            isGameOver = !board.createNewBrick();
            if (isGameOver) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());

        } else if (event.getEventSource() == EventSource.USER) {
            board.getScore().add(1);
        }
        return new DownData(clearRow, board.getViewData(), isGameOver);
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
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}
