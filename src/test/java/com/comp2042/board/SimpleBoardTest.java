package com.comp2042.board;

import com.comp2042.logic.collision.ClearRow;
import com.comp2042.controller.GameConfiguration;
import com.comp2042.logic.board.ViewData;
import com.comp2042.logic.rules.ClassicModeRules;
import com.comp2042.logic.rules.SurvivalModeRules;
import com.comp2042.logic.rules.ZenModeRules;
import com.comp2042.model.board.SimpleBoard;
import com.comp2042.model.bricks.core.Brick;
import com.comp2042.model.bricks.core.BrickGenerator;
import com.comp2042.model.bricks.tetromino.IBrick;
import com.comp2042.model.bricks.tetromino.RandomBrickGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBoardTest {

    @Test
    void moveBrickDownFreely() {
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
        board.createNewBrick();
        boolean moved = board.moveBrickDown();
        assertTrue(moved);
    }

    // Check when brick hits floor
    @Test
    void moveBrickDownCollides() {
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
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
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
        board.createNewBrick();
        boolean moved = board.moveBrickLeft();
        assertTrue(moved);
    }

    // Check when brick hits wall
    @Test
    void moveBrickLeftCollides() {
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
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
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
        board.createNewBrick();
        boolean moved = board.moveBrickRight();
        assertTrue(moved);
    }

    // Check when brick hits wall
    @Test
    void moveBrickRightCollides() {
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
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
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
        board.createNewBrick();
        boolean rotated = board.rotateBrickCounterClockwise();
        assertTrue(rotated);
    }

    // Will amend test – issue is due to randomness in brick generation
    @Test
    void rotateCollides() {
        BrickGenerator forceBrick = new BrickGenerator() {
            @Override
            public Brick getBrick() {
                return new IBrick();
            }

            @Override
            public Brick getNextBrick() {
                return new IBrick();
            }
        };
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT,  GameConfiguration.SIMPLE_BOARD_WIDTH, forceBrick);
        int[][] matrix = board.getBoardMatrix();
        for (int col = 0; col < GameConfiguration.SIMPLE_BOARD_WIDTH; col++) {
            matrix[3][col] = 1;
        }
        board.createNewBrick();
        boolean rotated = board.rotateBrickCounterClockwise();
        assertFalse(rotated);
    }

    @Test
    void createNewBrickShouldNotCollide() {
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
        boolean spawned = board.createNewBrick();
        assertTrue(spawned);
    }

    // New error found in code – supposed to collide but does not. Indicates a real issue in logic.
    @Test
    void createNewBrickShouldCollide() {
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
        int[][] matrix = board.getBoardMatrix();
        for (int row = 0; row < GameConfiguration.SIMPLE_BOARD_HEIGHT; row++) {
            for (int col = 0; col < GameConfiguration.SIMPLE_BOARD_WIDTH; col++) {
                matrix[row][col] = 1;
            }
        }
        boolean spawned = board.createNewBrick();
        assertFalse(spawned);
    }

    @Test
    void getViewData() {
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
        board.createNewBrick();
        ViewData viewData = board.getViewData();
        assertNotNull(viewData);
        assertNotNull(viewData.getBrickData());
        assertTrue(viewData.getBrickData().length > 0);
        assertTrue(viewData.getxPosition() >= 0 && viewData.getxPosition() < GameConfiguration.SIMPLE_BOARD_WIDTH);
        assertTrue(viewData.getyPosition() >= 0 && viewData.getyPosition() < GameConfiguration.SIMPLE_BOARD_HEIGHT);
    }

    // Brick shouldn't be able to move after it merges to background
    @Test
    void mergeTheBricksToBackgroundTest() {
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
        board.createNewBrick();
        board.mergeBrickToBackground();
        boolean moved = board.moveBrickLeft();
        assertFalse(moved);
    }

    // New error found in code – row is supposed to clear. Issue is likely due to the mix of using [cols][rows] and [rows][cols] interchangeably within the game.
    @Test
    void clearRowTest() {
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
        board.setRules(new ClassicModeRules());
        int[][] matrix = board.getBoardMatrix();
        for (int col = 0; col < GameConfiguration.SIMPLE_BOARD_WIDTH; col++) {
            matrix[19][col] = 1;
        }
        ClearRow result = board.clearRows();
        assertEquals(1, result.getLinesRemoved());
        int[][] updated = board.getBoardMatrix();
        for (int col = 0; col < GameConfiguration.SIMPLE_BOARD_WIDTH; col++) {
            assertEquals(0, updated[19][col]);
        }
    }

    @Test
    void newGameResetsEverything() {
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
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
        assertEquals(0, board.getLines().getLines());
        assertEquals(1, board.getLevel().getLevel());
    }

    @Test
    void classicLinesAndLevelUpdate() {
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
        board.setRules(new ClassicModeRules());
        int[][] matrix = board.getBoardMatrix();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < GameConfiguration.SIMPLE_BOARD_WIDTH; col++) {
                matrix[row][col] = 1;
            }
        }
        board.clearRows();
        assertEquals(10, board.getLines().getLines());
        assertEquals(2, board.getLevel().getLevel());
    }

    @Test
    void zenLinesAndLevelUpdate() {
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
        board.setRules(new ZenModeRules());
        int[][] matrix = board.getBoardMatrix();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < GameConfiguration.SIMPLE_BOARD_WIDTH; col++) {
                matrix[row][col] = 1;
            }
        }
        board.clearRows();
        assertEquals(10, board.getLines().getLines());
        assertEquals(2, board.getLevel().getLevel());
    }

    @Test
    void survivalLinesAndLevelUpdate() {
        SimpleBoard board = new SimpleBoard(GameConfiguration.SIMPLE_BOARD_HEIGHT, GameConfiguration.SIMPLE_BOARD_WIDTH, new RandomBrickGenerator());
        board.setRules(new SurvivalModeRules());
        int[][] matrix = board.getBoardMatrix();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < GameConfiguration.SIMPLE_BOARD_WIDTH; col++) {
                matrix[row][col] = 1;
            }
        }
        board.clearRows();
        assertEquals(10, board.getLines().getLines());
        assertEquals(2, board.getLevel().getLevel());
    }

    @Test
    void testIfClassicSpeedDelayDecreases() {
        ClassicModeRules rules = new ClassicModeRules();
        double level1Delay = rules.getSpeedDelay(1);
        double level2Delay = rules.getSpeedDelay(2);
        assertTrue(level1Delay > level2Delay);
    }

    @Test
    void testIfZenSpeedDelayDecreases() {
        ZenModeRules rules = new ZenModeRules();
        double level1Delay = rules.getSpeedDelay(1);
        double level2Delay = rules.getSpeedDelay(2);
        assertFalse(level1Delay > level2Delay);
    }

    @Test
    void testIfSurvivalSpeedDelayDecreases() {
        SurvivalModeRules rules = new SurvivalModeRules();
        double level1Delay = rules.getSpeedDelay(1);
        double level2Delay = rules.getSpeedDelay(2);
        assertFalse(level1Delay > level2Delay);
    }
}