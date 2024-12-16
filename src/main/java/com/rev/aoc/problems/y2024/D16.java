package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.io.load.LoaderUtils;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.util.geom.Direction;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class D16 extends AocProblem {

    public static final char START_CHAR = 'S';
    public static final char END_CHAR = 'E';
    public static final long ROT_COST = 1000L;

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 16);
    }

    @Override
    protected long partOneImpl() {
        List<String> lines = loadResources();
        char[][] maze = LoaderUtils.linesToCharMatrix(lines);
        int[] start = LoaderUtils.findOneAndOnly(maze, START_CHAR);
        int[] end = LoaderUtils.findOneAndOnly(maze, END_CHAR);

        Map<Direction, Long>[][] scores = computeScore(maze, start, Direction.RIGHT);
        Map<Direction, Long> endScores = scores[end[0]][end[1]];
        return endScores.values().stream().min(Long::compare).get();
    }

    @Override
    protected long partTwoImpl() {
        return -1L;
    }

    private Map<Direction, Long>[][] computeScore(final char[][] maze, final int[] start, final Direction direction) {
        int height = maze.length;
        int width = maze[0].length;
        Map<Direction, Long>[][] scores = LoaderUtils.emptyArray(new Map[1][1], height, width, () -> new HashMap());

        Direction dir = direction;
        long rotScore = 0;
        for (int i = 0; i < Direction.DIRECTIONS.length; i++) {
            scores[start[0]][start[1]].put(dir, rotScore);
            dir = Direction.next(dir);
            if (i == 2) { //hacky, tidy up later
                rotScore -= ROT_COST;
            } else {
                rotScore += ROT_COST;
            }
        }

        Set<Pair<Integer, Integer>> frontiere = new HashSet<>();
        frontiere.add(Pair.of(start[0], start[1]));
        while (!frontiere.isEmpty()) {
            Set<Pair<Integer, Integer>> nextFrontiere = new HashSet<>();
            Iterator<Pair<Integer, Integer>> it = frontiere.iterator();
            while (it.hasNext()) {
                Pair<Integer, Integer> front = it.next();
                for (int dirIndex = 0; dirIndex < Direction.DIRECTIONS.length; dirIndex++) {
                    dir = Direction.get(dirIndex);
                    int nextI = front.getLeft() + dir.getI();
                    int nextJ = front.getRight() + dir.getJ();
                    if (maze[nextI][nextJ] == '#') {
                        continue;
                    }
                    Map<Direction, Long> cellScores = scores[nextI][nextJ];
                    long cellScore = scores[front.getLeft()][front.getRight()].get(dir) + 1;
                    for (int i = 0; i < Direction.DIRECTIONS.length; i++) {
                        long bestCellScore = cellScores.getOrDefault(dir, Long.MAX_VALUE);
                        if (cellScore < bestCellScore) {
                            cellScores.put(dir, cellScore);
                            nextFrontiere.add(Pair.of(nextI, nextJ));
                        }
                        dir = Direction.next(dir);
                        if (i == 2) { //hacky, tidy up later
                            cellScore -= ROT_COST;
                        } else {
                            cellScore += ROT_COST;
                        }
                    }
                }
            }
            frontiere = nextFrontiere;
        }
        return scores;
    }
}
