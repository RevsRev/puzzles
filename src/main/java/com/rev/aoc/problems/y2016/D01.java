package com.rev.aoc.problems.y2016;

import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.framework.problem.ProblemExecutionException;
import com.rev.aoc.util.geom.Direction;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Set;

import com.rev.aoc.framework.problem.ResourceLoader;

public final class D01 extends AocProblem<Integer, Integer> {

    public static final String LEFT = "L";

    @AocProblemI(year = 2016, day = 1, part = 1)
    @Override
    protected Integer partOneImpl(final ResourceLoader resourceLoader) {
        String[] directions = resourceLoader.resources().get(0).replaceAll("\\s+", "").split(",");

        int x = 0;
        int y = 0;
        Direction dir = Direction.UP;

        for (int i = 0; i < directions.length; i++) {
            String direction = directions[i];
            if (direction.startsWith(LEFT)) {
                dir = dir.previous();
            } else {
                dir = dir.next();
            }
            int amount = Integer.parseInt(direction.substring(1));
            x += dir.getI() * amount;
            y += dir.getJ() * amount;
        }

        return Math.abs(x) + Math.abs(y);
    }

    @AocProblemI(year = 2016, day = 1, part = 2)
    @Override
    protected Integer partTwoImpl(final ResourceLoader resourceLoader) {
        String[] directions = resourceLoader.resources().get(0).replaceAll("\\s+", "").split(",");

        Set<Pair<Integer, Integer>> visited = new HashSet<>();

        int x = 0;
        int y = 0;
        Direction dir = Direction.UP;

        for (int i = 0; i < directions.length; i++) {
            String direction = directions[i];
            if (direction.startsWith("L")) {
                dir = dir.previous();
            } else {
                dir = dir.next();
            }
            int amount = Integer.parseInt(direction.substring(1));
            for (int j = 1; j <= amount; j++) {
                x += dir.getI();
                y += dir.getJ();
                if (visited.contains(Pair.of(x, y))) {
                    return Math.abs(x) + Math.abs(y);
                } else {
                    visited.add(Pair.of(x, y));
                }
            }
        }

        throw new ProblemExecutionException("Solution not found");
    }
}
