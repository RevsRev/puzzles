package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.io.load.LoaderUtils;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.util.geom.Direction;
import org.apache.commons.lang3.tuple.Pair;

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

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    protected long partTwoImpl() {
        List<String> lines = loadResources();
        char[][] maze = LoaderUtils.linesToCharMatrix(lines);
        int[] start = LoaderUtils.findOneAndOnly(maze, START_CHAR);
        int[] end = LoaderUtils.findOneAndOnly(maze, END_CHAR);

        Map<Direction, Long>[][] scores = computeScore(maze, start, Direction.RIGHT);
        Map<Direction, Long> endScores = scores[end[0]][end[1]];
        long minScore = endScores.values().stream().min(Long::compare).get();

        Set<Pair<Integer, Integer>> optimalTiles = new HashSet<>();
        for (Map.Entry<Direction, Long> entry : endScores.entrySet()) {
            if (entry.getValue() == minScore) {
                backTrack(scores, end, entry.getKey(), optimalTiles);
            }
        }
        return optimalTiles.size();
    }

    private void backTrack(final Map<Direction, Long>[][] scores,
                           final int[] position,
                           final Direction direction,
                           final Set<Pair<Integer, Integer>> optimalTiles) {
        optimalTiles.add(Pair.of(position[0], position[1]));
        if (scores[position[0]][position[1]].get(direction) == 0) {
            return;
        }

        Direction dir = direction;
        final long score = scores[position[0]][position[1]].get(direction);

        //First check if we've rotated on this same spot
        for (int dirIndex = 0; dirIndex < Direction.DIRECTIONS.length; dirIndex++) {
            int i = position[0];
            int j = position[1];

            long rotationCost = 0;
            if (dirIndex != 0) {
                rotationCost += ROT_COST;
            }
            if (dirIndex == 2) {
                rotationCost += ROT_COST;
            }

            long targetScore = score - rotationCost;
            if (dirIndex != 0 && scores[i][j].get(dir) == targetScore) {
                backTrack(scores, position, dir, optimalTiles);
            }
            dir = Direction.previous(dir);
        }

        //Now check if we could have come from an adjacent cell
        long targetScore = score - 1;
        int prevI = position[0] - dir.getI();
        int prevJ = position[1] - dir.getJ();
        if (scores[prevI][prevJ].containsKey(dir) && scores[prevI][prevJ].get(dir) == targetScore) {
            backTrack(scores, new int[]{prevI, prevJ}, dir, optimalTiles);
        }
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
