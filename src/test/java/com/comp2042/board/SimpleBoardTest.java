package com.comp2042.board;

import com.comp2042.logic.ClearRow;
import com.comp2042.logic.ViewData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpleBoardTest {

    @Test
    void moveBrickDownFreely() {
        SimpleBoard board = new SimpleBoard(10,20);
        board.createNewBrick();
        boolean moved = board.moveBrickDown();
        assertTrue(moved);
    }

    // Check when brick hits floor
    @Test
    void moveBrickDownCollides() {
        SimpleBoard board = new SimpleBoard(10,20);
        board.createNewBrick();
        assertNotNull(board.getViewData());
        boolean moved = true;
        while (moved) {
            moved = board.moveBrickDown();
        }
        boolean result = board.moveBrickDown();
        assertFalse(result);
    }

    @Test
    void moveBrickLeftFreely() {
        SimpleBoard board = new SimpleBoard(10,20);
        board.createNewBrick();
        boolean moved = board.moveBrickLeft();
        assertTrue(moved);
    }

    // Check when brick hits wall
    @Test
    void moveBrickLeftCollides() {
        SimpleBoard board = new SimpleBoard(10,20);
        board.createNewBrick();
        assertNotNull(board.getViewData());
        boolean moved = true;
        while (moved) {
            moved = board.moveBrickLeft();
        }
        boolean result = board.moveBrickLeft();
        assertFalse(result);
    }

    @Test
    void moveBrickRightFreely() {
        SimpleBoard board = new SimpleBoard(10,20);
        board.createNewBrick();
        boolean moved = board.moveBrickRight();
        assertTrue(moved);
    }

    // Check when brick hits wall
    @Test
    void moveBrickRightCollides() {
        SimpleBoard board = new SimpleBoard(10,20);
        board.createNewBrick();
        assertNotNull(board.getViewData());
        boolean moved = true;
        while (moved) {
            moved = board.moveBrickRight();
        }
        boolean result = board.moveBrickRight();
        assertFalse(result);
    }

    @Test
    void rotateFreely() {
        SimpleBoard board = new SimpleBoard(10,20);
        board.createNewBrick();
        boolean rotated = board.rotateLeftBrick();
        assertTrue(rotated);
    }

    // Will amend test – issue is due to randomness in brick generation
//    @Test
//    void rotateCollides() {
//        SimpleBoard board = new SimpleBoard(10,20);
//        int[][] matrix = board.getBoardMatrix();
//        for (int i = 0; i < 10; i++) {
//            matrix[i][2] = 1;
//        }
//        board.createNewBrick();
//        boolean rotated = board.rotateLeftBrick();
//        assertFalse(rotated);
//    }

    @Test
    void createNewBrickShouldNotCollide() {
        SimpleBoard board = new SimpleBoard(10,20);
        boolean collided = board.createNewBrick();
        assertFalse(collided);
    }

    // New error found in code – supposed to collide but does not. Indicates a real issue in logic.
    @Test
    void createNewBrickShouldCollide() {
        SimpleBoard board = new SimpleBoard(10,20);
        int[][] matrix = board.getBoardMatrix();
        for (int i = 0; i < 10; i++) {
            matrix[i][2] = 1;
        }
        boolean collided = board.createNewBrick();
        assertTrue(collided);
    }

    @Test
    void getViewData() {
        SimpleBoard board = new SimpleBoard(10,20);
        board.createNewBrick();
        ViewData viewData = board.getViewData();
        assertNotNull(viewData);
        assertNotNull(viewData.getBrickData());
        assertTrue(viewData.getBrickData().length > 0);
        assertTrue(viewData.getxPosition() >= 0 && viewData.getxPosition() < 10);
        assertTrue(viewData.getyPosition() >= 0 && viewData.getyPosition() < 20);
    }

    // Brick shouldn't be able to move after it merges to background
    @Test
    void mergeTheBricksToBackgroundTest() {
        SimpleBoard board = new SimpleBoard(10, 20);
        board.createNewBrick();
        board.mergeBrickToBackground();
        boolean moved = board.moveBrickDown();
        assertFalse(moved);
    }

    // New error found in code – row is supposed to clear. Issue is likely due to the mix of using [cols][rows] and [rows][cols] interchangeably within the game.
    @Test
    void clearRowTest() {
        SimpleBoard board = new SimpleBoard(10, 20);
        int[][] m = board.getBoardMatrix();
        for (int i = 0; i < 10; i++) {
            m[i][19] = 1;
        }
        ClearRow result = board.clearRows();
        assertEquals(1, result.getLinesRemoved());
        int[][] updated = board.getBoardMatrix();
        for (int i = 0; i < 10; i++) {
            assertEquals(0, updated[i][19]);
        }
    }

    @Test
    void newGameResetsEverything() {
        SimpleBoard board = new SimpleBoard(10, 20);
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