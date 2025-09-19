package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings({"checkstyle:FinalParameters", "checkstyle:VisibilityModifier"})
public final class L037 {
    private static final int SQUARE_SIZE = 3;
    private static final int BOARD_SIZE = 3 * SQUARE_SIZE;
    private static final Point[][] KEYS = createKeys();
    private static final Comparator<Pair<Point, Set<Character>>> COMPARATOR =
            Comparator.comparingInt(a -> a.right.size());
    private static final List<Character> CELL_VALUES = List.of('1', '2', '3', '4', '5', '6', '7', '8', '9');

    private static boolean resolveCounts(final Map<Point, Set<Character>> board,
                                         final Map<Character, List<Point>> counts) {
        boolean needsSorting = false;
        for (final char c : counts.keySet()) {
            if (counts.get(c).size() != 1) {
                continue;
            }
            final Set<Character> chars = board.get(counts.get(c).get(0));
            if (chars.size() <= 1) {
                if (!chars.contains(c)) {
                    chars.clear(); //invalid solution, so we clear the cell out and fail later
                    needsSorting = true;
                }
            } else {
                chars.clear();
                chars.add(c);
                needsSorting = true;
            }
        }
        return needsSorting;
    }

    private static Point[][] createKeys() {
        final Point[][] keys = new Point[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                keys[i][j] = new Point(i, j);
            }
        }
        return keys;
    }

    @LeetProblem(number = 37)
    public Object[] apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        solveSudoku((char[][]) problemResourceLoader.resources()[0]);
        return problemResourceLoader.resources();
    }

    public void solveSudoku(final char[][] board) {
        final Map<Point, Character> inputPoints = parseInput(board);

        //Initialise unsolved state
        final Map<Point, Character> solvedPoints = new HashMap<>();
        final List<Pair<Point, Set<Character>>> unsolvedPoints = createUnsolvedPoints();
        final Map<Point, Set<Character>> unsolvedBoard = createBoard(solvedPoints, unsolvedPoints);

        //Transfer our input
        for (int i = 0; i < unsolvedPoints.size(); i++) {
            final Point p = unsolvedPoints.get(i).left;
            if (inputPoints.containsKey(p)) {
                final Set<Character> chars = unsolvedPoints.get(i).right;
                chars.clear();
                chars.add(inputPoints.get(p));
            }
        }
        solveSodoku(unsolvedBoard, solvedPoints, unsolvedPoints);
        substituteReturnedResult(solvedPoints, board);
    }

    private boolean solveSodoku(final Map<Point, Set<Character>> board, final Map<Point, Character> solvedPoints,
                                final List<Pair<Point, Set<Character>>> unsolvedPoints) {
        final boolean validSoFar = sortDeterministically(board, solvedPoints, unsolvedPoints);

        if (!validSoFar) {
            return false;
        }

        if (unsolvedPoints.size() > 0) {
            final Pair<Point, Set<Character>> smallest = unsolvedPoints.get(0);
            final Set<Character> chars = Set.copyOf(smallest.right);
            for (final char c : chars) {
                final Map<Point, Character> solvedPointsCopy = new HashMap<>();
                final List<Pair<Point, Set<Character>>> unsolvedPointsCopy = new ArrayList<>();
                copySolvedPoints(solvedPoints, solvedPointsCopy);
                copyUnsolvedPoints(unsolvedPoints, unsolvedPointsCopy);
                final Map<Point, Set<Character>> boardCopy = createBoard(solvedPointsCopy, unsolvedPointsCopy);

                final Pair<Point, Set<Character>> smallestCopy = unsolvedPointsCopy.get(0);
                smallestCopy.right.clear();
                smallestCopy.right.add(c);
                final boolean success = solveSodoku(boardCopy, solvedPointsCopy, unsolvedPointsCopy);
                if (success) {
                    copySolvedPoints(solvedPointsCopy, solvedPoints);
                    copyUnsolvedPoints(unsolvedPointsCopy, unsolvedPoints);
                    copyToBoard(board, solvedPoints, unsolvedPoints);
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private boolean sortDeterministically(final Map<Point, Set<Character>> board,
                                          final Map<Point, Character> solvedPoints,
                                          final List<Pair<Point, Set<Character>>> unsolvedPoints) {
        unsolvedPoints.sort(COMPARATOR);
        while (unsolvedPoints.size() > 0 && unsolvedPoints.get(0).right.size() == 1) {

            boolean needsSorting = false;

            //exhaust our list of points that need to be moved from unsolved to solved
            while (unsolvedPoints.size() > 0 && unsolvedPoints.get(0).right.size() == 1) {
                final Pair<Point, Set<Character>> solvedSet = unsolvedPoints.remove(0);
                final Pair<Point, Character> solved = new Pair<>(solvedSet.left, solvedSet.right.iterator().next());
                solvedPoints.put(solved.left, solved.right);
                final boolean rowResult = removeCandidateFromLine(board, solved, solvedPoints, false);
                final boolean colResult = removeCandidateFromLine(board, solved, solvedPoints, true);
                final boolean squareResult = removeCandidateFromSquare(board, solved, solvedPoints);
                needsSorting = needsSorting || rowResult || colResult || squareResult;
            }

            //Build our list back up
            boolean keepResolving = true;
            while (keepResolving) {
                final boolean rowResult = resolveLine(board, solvedPoints, false);
                final boolean colResult = resolveLine(board, solvedPoints, true);
                final boolean squareResult = resolveSquares(board, solvedPoints);
                keepResolving = rowResult || squareResult || colResult;
                needsSorting |= keepResolving;
            }


            if (unsolvedPoints.size() > 0 && needsSorting) {
                unsolvedPoints.sort(COMPARATOR);
            }
        }

        if (unsolvedPoints.size() == 0) {
            return true;
        }
        return unsolvedPoints.get(0).right.size() > 1;
    }

    private boolean resolveLine(final Map<Point, Set<Character>> board, final Map<Point, Character> solvedPoints,
                                final boolean col) {
        boolean needsSorting = false;
        for (int j = 0; j < BOARD_SIZE; j++) {
            final Map<Character, List<Point>> counts = new HashMap<>();
            for (int i = 0; i < BOARD_SIZE; i++) {
                Point key = KEYS[i][j];
                if (col) {
                    key = KEYS[j][i];
                }
                if (solvedPoints.containsKey(key)) {
                    continue;
                }
                final Set<Character> chars = board.get(key);
                for (final char c : chars) {
                    counts.computeIfAbsent(c, (k) -> new ArrayList<>()).add(key);
                }
            }
            if (resolveCounts(board, counts)) {
                needsSorting = true;
            }
        }
        return needsSorting;
    }

    private boolean resolveSquares(final Map<Point, Set<Character>> board, final Map<Point, Character> solvedPoints) {
        boolean needsSorting = false;
        for (int i = 0; i < SQUARE_SIZE; i++) {
            for (int j = 0; j < SQUARE_SIZE; j++) {
                needsSorting = resolveSquare(i, j, board, solvedPoints);
            }
        }
        return needsSorting;
    }

    private boolean resolveSquare(final int xL, final int yL, final Map<Point, Set<Character>> board,
                                  final Map<Point, Character> solvedPoints) {
        final Map<Character, List<Point>> counts = new HashMap<>();
        for (int i = 0; i < SQUARE_SIZE; i++) {
            for (int j = 0; j < SQUARE_SIZE; j++) {
                final Point key = KEYS[xL * SQUARE_SIZE + i][yL * SQUARE_SIZE + j];
                if (solvedPoints.containsKey(key)) {
                    continue;
                }
                final Set<Character> chars = board.get(key);
                for (final char c : chars) {
                    counts.computeIfAbsent(c, (k) -> new ArrayList<>()).add(key);
                }
            }
        }
        return resolveCounts(board, counts);
    }

    private boolean removeCandidateFromLine(final Map<Point, Set<Character>> board, final Pair<Point, Character> solved,
                                            final Map<Point, Character> solvedPoints, final boolean col) {
        boolean needsSorting = false;
        for (int i = 0; i < BOARD_SIZE; i++) {
            Point key = KEYS[solved.left.left][i];
            if (col) {
                key = KEYS[i][solved.left.right];
            }
            if (solvedPoints.containsKey(key)) {
                continue;
            }
            final Set<Character> vals = board.get(key);
            if (vals.remove(solved.right)) {
                needsSorting = true;
            }
        }
        return needsSorting;
    }

    private boolean removeCandidateFromSquare(final Map<Point, Set<Character>> board,
                                              final Pair<Point, Character> solved,
                                              final Map<Point, Character> solvedPoints) {
        final int xL = (solved.left.left / SQUARE_SIZE) * SQUARE_SIZE;
        final int xR = xL + SQUARE_SIZE;
        final int yL = (solved.left.right / SQUARE_SIZE) * SQUARE_SIZE;
        final int yR = yL + SQUARE_SIZE;

        boolean needsSorting = false;
        for (int i = xL; i < xR; i++) {
            for (int j = yL; j < yR; j++) {
                final Point key = KEYS[i][j];
                if (solvedPoints.containsKey(key)) {
                    continue;
                }
                final Set<Character> vals = board.get(key);
                if (vals.remove(solved.right)) {
                    needsSorting = true;
                }
            }
        }
        return needsSorting;
    }

    private Map<Point, Character> parseInput(final char[][] board) {
        final Map<Point, Character> inputPoints = new HashMap<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != '.') {
                    inputPoints.put(KEYS[i][j], board[i][j]);
                }
            }
        }
        return inputPoints;
    }

    private List<Pair<Point, Set<Character>>> createUnsolvedPoints() {
        final List<Pair<Point, Set<Character>>> unsolved = new LinkedList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                unsolved.add(new Pair(KEYS[i][j], new HashSet(CELL_VALUES)));
            }
        }
        return unsolved;
    }

    private Map<Point, Set<Character>> createBoard(final Map<Point, Character> solvedPoints,
                                                   final List<Pair<Point, Set<Character>>> unsolvedPoints) {
        final Map<Point, Set<Character>> board = new HashMap<>();
        copyToBoard(board, solvedPoints, unsolvedPoints);
        return board;
    }

    private void copyToBoard(final Map<Point, Set<Character>> board, final Map<Point, Character> solvedPoints,
                             final List<Pair<Point, Set<Character>>> unsolvedPoints) {
        final Iterator<Point> it = solvedPoints.keySet().iterator();
        while (it.hasNext()) {
            final Point p = it.next();
            board.computeIfAbsent(p, k -> new HashSet<>()).add(solvedPoints.get(p));
        }

        for (int i = 0; i < unsolvedPoints.size(); i++) {
            final Point p = unsolvedPoints.get(i).left;
            board.put(p, unsolvedPoints.get(i).right);
        }
    }

    private void copyUnsolvedPoints(final List<Pair<Point, Set<Character>>> unsolvedPoints,
                                    final List<Pair<Point, Set<Character>>> unsolvedPointsCopy) {
        unsolvedPointsCopy.clear();
        for (int i = 0; i < unsolvedPoints.size(); i++) {
            final Point p = unsolvedPoints.get(i).left;
            final Set<Character> toCopy = unsolvedPoints.get(i).right;
            final Set<Character> copy = new HashSet<>(toCopy);
            unsolvedPointsCopy.add(new Pair(p, copy));
        }
    }

    private void copySolvedPoints(final Map<Point, Character> solvedPoints,
                                  final Map<Point, Character> solvedPointsCopy) {
        solvedPointsCopy.clear();
        final Iterator<Point> it = solvedPoints.keySet().iterator();
        while (it.hasNext()) {
            final Point p = it.next();
            solvedPointsCopy.put(p, solvedPoints.get(p));
        }
    }

    private void substituteReturnedResult(final Map<Point, Character> solvedPoints, final char[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                final Pair key = KEYS[i][j];
                if (solvedPoints.containsKey(key)) {
                    board[i][j] = solvedPoints.get(key);
                }
            }
        }
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
