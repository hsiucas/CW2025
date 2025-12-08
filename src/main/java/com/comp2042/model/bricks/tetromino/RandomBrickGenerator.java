package com.comp2042.model.bricks.tetromino;

import com.comp2042.model.bricks.core.Brick;
import com.comp2042.model.bricks.core.BrickGenerator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A concrete implementation of {@link BrickGenerator} that provides a random sequence of bricks.
 * It maintains a queue of upcoming bricks to allow for the "Next Brick" preview feature.
 */
public class RandomBrickGenerator implements BrickGenerator {

    private final List<Brick> brickList;
    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    /**
     * Constructs a RandomBrickGenerator.
     * Initializes the list of available brick types and pre-fills the queue with
     * random bricks so that a preview is immediately available.
     */
    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());
        brickList.add(new JBrick());
        brickList.add(new LBrick());
        brickList.add(new OBrick());
        brickList.add(new SBrick());
        brickList.add(new TBrick());
        brickList.add(new ZBrick());

        // Pre-fill the buffer
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
    }

    /**
     * Retrieves the next brick from the queue and replenishes the queue if necessary.
     *
     * @return The next {@link Brick} to be used in the game.
     */
    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 3) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
        return nextBricks.poll();
    }

    /**
     * Generates a list of the matrices for the next 3 bricks in the queue.
     * Used by the view to render the "Next Piece" preview.
     *
     * @return A list of integer arrays representing the shapes of the next 3 bricks.
     */
    public List<int[][]> getNextThreeBricks() {
        List<int[][]> list = new ArrayList<>();
        Iterator<Brick> iterator = nextBricks.iterator();
        int counter = 0;
        while (iterator.hasNext() && counter < 3) {
            list.add(iterator.next().getShapeMatrix().get(0));
            counter++;
        }
        return list;
    }

    /**
     * Peeks at the very next brick without removing it from the queue.
     *
     * @return The next {@link Brick} instance.
     */
    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }
}