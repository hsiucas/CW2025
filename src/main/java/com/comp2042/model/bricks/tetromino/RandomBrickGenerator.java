package com.comp2042.model.bricks.tetromino;

import com.comp2042.model.bricks.core.Brick;
import com.comp2042.model.bricks.core.BrickGenerator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBrickGenerator implements BrickGenerator {

    private final List<Brick> brickList;

    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());
        brickList.add(new JBrick());
        brickList.add(new LBrick());
        brickList.add(new OBrick());
        brickList.add(new SBrick());
        brickList.add(new TBrick());
        brickList.add(new ZBrick());
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
    }

    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 3) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
        return nextBricks.poll();
    }

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

    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }
}
