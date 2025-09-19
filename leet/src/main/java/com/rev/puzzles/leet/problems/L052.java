package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("checkstyle:VisibilityModifier")
public final class L052 {
    @LeetProblem(number = 52)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return totalNQueens((int) problemResourceLoader.resources()[0]);
    }

    public int totalNQueens(final int n) {
        final List<List<String>> solutions = solveNQueens(n);
        return solutions.size();
    }

    public List<List<String>> solveNQueens(final int n) {
        final Set<Set<Point>> solutions = new HashSet<>();
        final Set<Point> queenPositions = new HashSet<>();
        final Set<Point> boardPoints = boardPoints(n);
        final Map<Point, Set<Point>> cachedQueenPoints = new HashMap<>();
        solveNQueens(n, 0, boardPoints, cachedQueenPoints, queenPositions, solutions);
        return formatSolutions(solutions);
    }

    private List<List<String>> formatSolutions(final Set<Set<Point>> solutions) {
        final List<List<String>> formattedSolutions = new ArrayList<>();
        for (final Set<Point> solution : solutions) {
            formattedSolutions.add(formatSolution(solution));
        }
        return formattedSolutions;
    }

    private List<String> formatSolution(final Set<Point> solution) {
        final List<String> rows = new ArrayList<>();
        for (int i = 0; i < solution.size(); i++) {
            final StringBuilder sb = new StringBuilder();
            for (int j = 0; j < solution.size(); j++) {
                if (solution.contains(new Point(i, j))) {
                    sb.append('Q');
                } else {
                    sb.append('.');
                }
            }
            rows.add(sb.toString());
        }
        return rows;
    }

    private void solveNQueens(final int n, final int level, final Set<Point> boardPoints,
                              final Map<Point, Set<Point>> cachedQueenPoints, final Set<Point> queenPositions,
                              final Set<Set<Point>> solutions) {
        for (int i = 0; i < n; i++) {
            final Point queenPos = new Point(level, i);
            if (!boardPoints.contains(queenPos)) {
                continue;
            }
            final Set<Point> queenPoints = getQueenPoint(queenPos, n, cachedQueenPoints);
            boolean shouldSkip = false;
            for (final Point queen : queenPositions) {
                if (queenPoints.contains(queen)) {
                    shouldSkip = true;
                    break;
                }
            }
            if (shouldSkip) {
                continue;
            }

            queenPositions.add(queenPos);
            if (level == n - 1) {
                solutions.add(new HashSet<>(queenPositions));
            } else {
                queenPoints.removeIf(p -> !boardPoints.contains(p));
                boardPoints.removeAll(queenPoints);
                solveNQueens(n, level + 1, boardPoints, cachedQueenPoints, queenPositions, solutions);
                boardPoints.addAll(queenPoints);
            }
            queenPositions.remove(queenPos);
        }
    }

    private Set<Point> boardPoints(final int n) {
        final Set<Point> retval = new HashSet<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                retval.add(new Point(i, j));
            }
        }
        return retval;
    }

    private Set<Point> getQueenPoint(final Point queenPos, final int n,
                                     final Map<Point, Set<Point>> cachedQueenPoints) {
//        if (cachedQueenPoints.containsKey(queenPos)) {
//            return cachedQueenPoints.get(queenPos);
//        }

        final Set<Point> retval = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (queenPos.left - i >= 0 && queenPos.right - i >= 0) {
                retval.add(new Point(queenPos.left - i, queenPos.right - i));
            }
            if (queenPos.left + i < n && queenPos.right - i >= 0) {
                retval.add(new Point(queenPos.left + i, queenPos.right - i));
            }
            if (queenPos.left - i >= 0 && queenPos.right + i < n) {
                retval.add(new Point(queenPos.left - i, queenPos.right + i));
            }
            if (queenPos.left + i < n && queenPos.right + i < n) {
                retval.add(new Point(queenPos.left + i, queenPos.right + i));
            }
            retval.add(new Point(i, queenPos.right));
            retval.add(new Point(queenPos.left, i));
        }
//        cachedQueenPoints.put(queenPos, retval);
        return retval;
    }


    private static class Pair<A, B> {
        A left;
        B right;

        Pair(final A left, final B right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(left, pair.left) && Objects.equals(right, pair.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }
    }

    private static class Point extends Pair<Integer, Integer> {
        Point(final Integer x, final Integer y) {
            super(x, y);
        }
    }

}
