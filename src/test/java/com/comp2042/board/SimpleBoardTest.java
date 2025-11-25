package com.comp2042.board;

import com.comp2042.logic.ClearRow;
import com.comp2042.logic.ViewData;
import org.junit.jupiter.api.RepeatedTest;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBoardTest {

    @RepeatedTest(10)
    void moveBrickDownFreely() {
        SimpleBoard board = new SimpleBoard(20,10);
        board.createNewBrick();
        boolean moved = board.moveBrickDown();
        assertTrue(moved);
    }

    // Check when brick hits floor
    @RepeatedTest(10)
    void moveBrickDownCollides() {
        SimpleBoard board = new SimpleBoard(20,10);
        board.createNewBrick();
        assertNotNull(board.getViewData());
        boolean moved = true;
        while (moved) {
            moved = board.moveBrickDown();
        }
        boolean result = board.moveBrickDown();
        assertFalse(result);
    }

    @RepeatedTest(10)
    void moveBrickLeftFreely() {
        SimpleBoard board = new SimpleBoard(20,10);
        board.createNewBrick();
        boolean moved = board.moveBrickLeft();
        assertTrue(moved);
    }

    // Check when brick hits wall
    @RepeatedTest(10)
    void moveBrickLeftCollides() {
        SimpleBoard board = new SimpleBoard(20,10);
        board.createNewBrick();
        assertNotNull(board.getViewData());
        boolean moved = true;
        while (moved) {
            moved = board.moveBrickLeft();
        }
        boolean result = board.moveBrickLeft();
        assertFalse(result);
    }

    @RepeatedTest(10)
    void moveBrickRightFreely() {
        SimpleBoard board = new SimpleBoard(20,10);
        board.createNewBrick();
        boolean moved = board.moveBrickRight();
        assertTrue(moved);
    }

    // Check when brick hits wall
    @RepeatedTest(10)
    void moveBrickRightCollides() {
        SimpleBoard board = new SimpleBoard(20,10);
        board.createNewBrick();
        assertNotNull(board.getViewData());
        boolean moved = true;
        while (moved) {
            moved = board.moveBrickRight();
        }
        boolean result = board.moveBrickRight();
        assertFalse(result);
    }

    @RepeatedTest(10)
    void rotateFreely() {
        SimpleBoard board = new SimpleBoard(20,10);
        board.createNewBrick();
        boolean rotated = board.rotateLeftBrick();
        assertTrue(rotated);
    }

    // Will amend test – issue is due to randomness in brick generation
//    @RepeatedTest20
//    void rotateCollides() {
//        SimpleBoard board = new SimpleBoard(20,10);
//        int[][] matrix = board.getBoardMatrix();
//        for (int col = 0; col < 10; col++) {
//            matrix[2][col] = 1;
//        }
//        board.createNewBrick();
//        boolean rotated = board.rotateLeftBrick();
//        assertFalse(rotated);
//    }

    @RepeatedTest(10)
    void createNewBrickShouldNotCollide() {
        SimpleBoard board = new SimpleBoard(20,10);
        boolean collided = board.createNewBrick();
        assertFalse(collided);
    }

    // New error found in code – supposed to collide but does not. Indicates a real issue in logic.
    @RepeatedTest(10)
    void createNewBrickShouldCollide() {
        SimpleBoard board = new SimpleBoard(20,10);
        int[][] matrix = board.getBoardMatrix();
        for (int col = 0; col < 10; col++) {
            matrix[2][col] = 1;
        }
        boolean collided = board.createNewBrick();
        assertTrue(collided);
    }

    @RepeatedTest(10)
    void getViewData() {
        SimpleBoard board = new SimpleBoard(20,10);
        board.createNewBrick();
        ViewData viewData = board.getViewData();
        assertNotNull(viewData);
        assertNotNull(viewData.getBrickData());
        assertTrue(viewData.getBrickData().length > 0);
        assertTrue(viewData.getxPosition() >= 0 && viewData.getxPosition() < 10);
        assertTrue(viewData.getyPosition() >= 0 && viewData.getyPosition() < 20);
    }

    // Brick shouldn't be able to move after it merges to background
    @RepeatedTest(10)
    void mergeTheBricksToBackgroundTest() {
        SimpleBoard board = new SimpleBoard(20,10);
        board.createNewBrick();
        board.mergeBrickToBackground();
        boolean moved = board.moveBrickLeft();
        assertFalse(moved);
    }

    // New error found in code – row is supposed to clear. Issue is likely due to the mix of using [cols][rows] and [rows][cols] interchangeably within the game.
    @RepeatedTest(10)
    void clearRowTest() {
        SimpleBoard board = new SimpleBoard(20,10);
        int[][] matrix = board.getBoardMatrix();
        for (int col = 0; col < 10; col++) {
            matrix[19][col] = 1;
        }
        ClearRow result = board.clearRows();
        assertEquals(1, result.getLinesRemoved());
        int[][] updated = board.getBoardMatrix();
        for (int col = 0; col < 10; col++) {
            assertEquals(0, updated[19][col]);
        }
    }

    @RepeatedTest(10)
    void newGameResetsEverything() {
        SimpleBoard board = new SimpleBoard(20,10);
        board.createNewBrick();
        board.mergeBrickToBackground();
        board.newGame();
        int[][] matrix = board.getBoardMatrix();
        for (int[] col : matrix) {
            for (int cell : col) {
                assertEquals(0, cell);
            }
        }
        assertEquals(0, board.getScore().scoreProperty().getValue());
    }
}