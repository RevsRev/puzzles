package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.io.load.LoaderUtils;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.util.geom.Direction;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rev.aoc.framework.problem.ResourceLoader;

public final class D16 extends AocProblem<Long, Long> {

    public static final char START_CHAR = 'S';
    public static final char END_CHAR = 'E';
    public static final long ROT_COST = 1000L;

    @AocProblemI(year = 2024, day = 16, part = 1)
    @Override
    protected Long partOneImpl(final ResourceLoader resourceLoader) {
        List<String> lines = resourceLoader.resources();
        char[][] maze = LoaderUtils.linesToCharMatrix(lines);
        int[] start = LoaderUtils.findOne(maze, START_CHAR);
        int[] end = LoaderUtils.findOne(maze, END_CHAR);

        Map<Direction, Long>[][] scores = computeScore(maze, start, Direction.RIGHT);
        Map<Direction, Long> endScores = scores[end[0]][end[1]];
        return endScores.values().stream().min(Long::compare).get();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @AocProblemI(year = 2024, day = 16, part = 2)
    @Override
    protected Long partTwoImpl(final ResourceLoader resourceLoader) {
        List<String> lines = resourceLoader.resources();
        char[][] maze = LoaderUtils.linesToCharMatrix(lines);
        int[] start = LoaderUtils.findOne(maze, START_CHAR);
        int[] end = LoaderUtils.findOne(maze, END_CHAR);

        Map<Direction, Long>[][] scores = computeScore(maze, start, Direction.RIGHT);
        Map<Direction, Long> endScores = scores[end[0]][end[1]];
        long minScore = endScores.values().stream().min(Long::compare).get();

        Set<Pair<Integer, Integer>> optimalTiles = new HashSet<>();
        for (Map.Entry<Direction, Long> entry : endScores.entrySet()) {
            if (entry.getValue() == minScore) {
                backTrack(scores, end, entry.getKey(), optimalTiles);
            }
        }
        return (long) optimalTiles.size();
    }

    private void backTrack(final Map<Direction, Long>[][] scores,
                           final int[] position,
                           final Direction direction,
                           final Set<Pair<Integer, Integer>> optimalTiles) {
        optimalTiles.add(Pair.of(position[0], position[1]));
        if (scores[position[0]][position[1]].get(direction) == 0) {
            return;
        }

        final long score = scores[position[0]][position[1]].get(direction);

        //First check if we've rotated on this same spot
        int numRotations = 0;
        for (Direction dir : direction) {
            int i = position[0];
            int j = position[1];

            long rotationCost = getRotationCost(numRotations);
            long targetScore = score - rotationCost;
            if (numRotations != 0 && scores[i][j].get(dir) == targetScore) {
                backTrack(scores, position, dir, optimalTiles);
            }
            numRotations++;
        }

        //Now check if we could have come from an adjacent cell
        long targetScore = score - 1;
        int prevI = position[0] - direction.getI();
        int prevJ = position[1] - direction.getJ();
        if (scores[prevI][prevJ].containsKey(direction) && scores[prevI][prevJ].get(direction) == targetScore) {
            backTrack(scores, new int[]{prevI, prevJ}, direction, optimalTiles);
        }
    }

    private static long getRotationCost(int numRotations) {
        long rotationCost = 0;
        if (numRotations != 0) {
            rotationCost += ROT_COST;
        }
        if (numRotations == 2) {
            rotationCost += ROT_COST;
        }
        return rotationCost;
    }

    private Map<Direction, Long>[][] computeScore(final char[][] maze, final int[] start, final Direction direction) {
        int height = maze.length;
        int width = maze[0].length;
        Map<Direction, Long>[][] scores = LoaderUtils.emptyMatrix(new Map[1][1], height, width, () -> new HashMap());

        Set<Pair<Integer, Integer>> frontiere = new HashSet<>();

        int i = start[0];
        int j = start[1];
        updateScores(direction, scores, i, j, 0, frontiere);

        while (!frontiere.isEmpty()) {
            Set<Pair<Integer, Integer>> nextFrontiere = new HashSet<>();
            Iterator<Pair<Integer, Integer>> it = frontiere.iterator();
            while (it.hasNext()) {
                Pair<Integer, Integer> front = it.next();
                for (Direction dir : Direction.UP) {
                    int nextI = front.getLeft() + dir.getI();
                    int nextJ = front.getRight() + dir.getJ();
                    if (maze[nextI][nextJ] == '#') {
                        continue;
                    }
                    long startCellScore = scores[front.getLeft()][front.getRight()].get(dir) + 1;
                    updateScores(dir, scores, nextI, nextJ, startCellScore, nextFrontiere);
                }
            }
            frontiere = nextFrontiere;
        }
        return scores;
    }

    private static void updateScores(final Direction direction,
                                     final Map<Direction, Long>[][] scores,
                                     final int i,
                                     final int j,
                                     final long startCellScore,
                                     final Set<Pair<Integer, Integer>> frontiere) {
        Map<Direction, Long> startCellScores = scores[i][j];
        int numRotations = 0;
        for (Direction dir : direction) {
            long bestCellScore = startCellScores.getOrDefault(dir, Long.MAX_VALUE);
            long cellScore = startCellScore + getRotationCost(numRotations);
            if (cellScore < bestCellScore) {
                startCellScores.put(dir, cellScore);
                frontiere.add(Pair.of(i, j));
            }
            numRotations++;
        }
    }
}
