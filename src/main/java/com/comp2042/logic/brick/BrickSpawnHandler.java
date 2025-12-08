package com.comp2042.logic.brick;

import com.comp2042.logic.board.MatrixOperations;

import java.awt.*;

/**
 * Calculates the starting spawn position for new bricks.
 * Handles logic for both standard top-down mode and 4-way center-spawn mode.
 */
public class BrickSpawnHandler {

    /**
     * Calculates the spawn point for a brick in standard "Gameboy" mode (Top-Center).
     * Checks for immediate collision (Game Over condition).
     *
     * @param boardMatrix The current game board.
     * @param boardWidth  The width of the board.
     * @param brick       The shape matrix of the new brick.
     * @return A {@link Point} representing the spawn coordinates, or null if spawning causes a collision.
     */
    public Point getGameboySpawnPoint(int[][] boardMatrix, int boardWidth, int[][] brick) {
        int brickWidth = brick[0].length;
        int x = (boardWidth - brickWidth) / 2;
        int y = 2;

        Point gameboySpawnPoint = new Point(x, y);

        if (MatrixOperations.intersect(boardMatrix, brick, gameboySpawnPoint.y, gameboySpawnPoint.x)) {
            return null;
        }
        return gameboySpawnPoint;
    }

    /**
     * Calculates the spawn point for a brick in 4-Way mode (Dead Center).
     *
     * @param boardMatrix The current game board.
     * @param boardWidth  The width of the board.
     * @param boardHeight The height of the board.
     * @param brick       The shape matrix of the new brick.
     * @return A {@link Point} representing the spawn coordinates, or null if spawning causes a collision.
     */
    public Point getCenterSpawnPoint(int[][] boardMatrix, int boardWidth, int boardHeight, int[][] brick) {
        int brickWidth = brick[0].length;
        int brickHeight = brick.length;
        int x = (boardWidth - brickWidth) / 2;
        int y = (boardHeight - brickHeight) / 2;

        Point centerSpawnPoint = new Point(x, y);

        if (MatrixOperations.intersect(boardMatrix, brick, centerSpawnPoint.y, centerSpawnPoint.x)) {
            return null;
        }
        return centerSpawnPoint;
    }
}