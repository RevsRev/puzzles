package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.util.geom.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import com.rev.aoc.framework.problem.ResourceLoader;

public final class D21 extends AocProblem<Long, Long> {

    private static final char[][] DOOR_KEYPAD_CHARS = {
            {'7', '8', '9'},
            {'4', '5', '6'},
            {'1', '2', '3'},
            {'.', '0', 'A'}
    };
    private static final char[][] ROBOT_KEYPAD_CHARS = {
            {'.', '^', 'A'},
            {'<', 'v', '>'}
    };

    private static final KeypadOperator DOOR_KEYPAD = new KeypadOperator(DOOR_KEYPAD_CHARS,
            (i, j) -> !(i == 3 && j == 0));
    private static final KeypadOperator ROBOT_KEYPAD = new KeypadOperator(ROBOT_KEYPAD_CHARS,
            (i, j) -> !(i == 0 && j == 0));
    private static final int PART_ONE_DEPTH = 3;
    private static final int PART_TWO_DEPTH = 26;

    @AocProblemI(year = 2024, day = 21, part = 1)
    @Override
    protected Long partOneImpl(final ResourceLoader resourceLoader) {
        List<String> codes = resourceLoader.resources();
        long totalComplexity = 0;
        for (String code : codes) {
            long minMoves = getMinimumNumberOfMovesForCode(code, PART_ONE_DEPTH);
            totalComplexity += getNumeric(code) * minMoves;
        }
        return totalComplexity;
    }

    @AocProblemI(year = 2024, day = 21, part = 2)
    @Override
    protected Long partTwoImpl(final ResourceLoader resourceLoader) {
        List<String> codes = resourceLoader.resources();
        long totalComplexity = 0;
        for (String code : codes) {
            long minMoves = getMinimumNumberOfMovesForCode(code, PART_TWO_DEPTH);
            totalComplexity += getNumeric(code) * minMoves;
        }
        return totalComplexity;
    }

    public long getNumeric(final String s) {
        return Long.parseLong(s.replaceAll("[^\\d.]", ""));
    }

    private long getMinimumNumberOfMovesForCode(final String code, final int depth) {
        HashMap<Integer, Map<Character, Map<Character, Long>>> movesCache = new HashMap<>();
        long score = 0;
        char startChar = 'A';
        for (char c : code.toCharArray()) {
            score += getMinimumNumberOfMoves(movesCache, startChar, c, depth);
            startChar = c;
        }
        return score;
    }

    private long getMinimumNumberOfMoves(final Map<Integer, Map<Character, Map<Character, Long>>> movesCache,
                                         char start,
                                         char end,
                                         int maxDepth) {
        return getMinimumNumberOfMoves(movesCache, start, end, maxDepth, maxDepth);
    }

    private long getMinimumNumberOfMoves(final Map<Integer, Map<Character, Map<Character, Long>>> movesCache,
                                         char start,
                                         char end,
                                         int maxDepth,
                                         int depth) {
        if (depth == 0) {
            return 1;
        }

        long computed = getFromCache(movesCache, depth, start, end);
        if (computed != Long.MAX_VALUE) {
            return computed;
        }

        KeypadOperator keypad = depth == maxDepth ? DOOR_KEYPAD : ROBOT_KEYPAD;

        List<String> moves = keypad.cache.get(start).get(end);
        for (String move : moves) {
            long score = 0;
            char moveStartChar = 'A';
            move = move + 'A';
            for (char c : move.toCharArray()) {
                score += getMinimumNumberOfMoves(movesCache, moveStartChar, c, maxDepth, depth - 1);
                moveStartChar = c;
            }
            long bestScore = getFromCache(movesCache, depth, start, end);
            if (score < bestScore) {
                putInCache(movesCache, depth, start, end, score);
            }
        }
        return getFromCache(movesCache, depth, start, end);
    }

    private static void putInCache(final Map<Integer, Map<Character, Map<Character, Long>>> movesCache,
                                   final int depth,
                                   final char start,
                                   final char end,
                                   final long value) {
        movesCache.computeIfAbsent(depth, k -> new HashMap<>())
                .computeIfAbsent(start, k -> new HashMap<>())
                .put(end, value);
    }

    private static long getFromCache(final Map<Integer, Map<Character, Map<Character, Long>>> movesCache,
                                     int depth,
                                     char start,
                                     char end) {
        return movesCache.computeIfAbsent(depth, k -> new HashMap<>())
                .computeIfAbsent(start, k -> new HashMap<>())
                .computeIfAbsent(end, k -> Long.MAX_VALUE);
    }

    private static final class KeypadOperator {
        private final char[][] keypad;
        private final int height;
        private final int width;
        private final BiFunction<Integer, Integer, Boolean> validator;
        private final Map<Character, Map<Character, List<String>>> cache = new HashMap<>();

        private KeypadOperator(final char[][] keypad, final BiFunction<Integer, Integer, Boolean> validator) {
            this.keypad = keypad;
            this.height = keypad.length;
            this.width = keypad[0].length;
            this.validator = validator;
            cacheMoves(); //doing work in constructor is bad, but hey ho it's Christmas!
        }

        public List<String> getMoves(char start, char end) {
            return cache.get(start).get(end);
        }

        private void cacheMoves() {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (keypad[i][j] != '.') {
                        cacheMoves(i, j);
                    }
                }
            }
        }

        private void cacheMoves(int startI, int startJ) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (keypad[i][j] != '.') {
                        List<String> s = getCacheStrings(startI, startJ, i, j);
                        cache.computeIfAbsent(keypad[startI][startJ], k -> new HashMap<>()).put(keypad[i][j], s);
                    }
                }
            }
        }

        private List<String> getCacheStrings(int startI, int startJ, int endI, int endJ) {
            Direction vertDir = startI >= endI ? Direction.UP : Direction.DOWN;
            Direction horizDir = startJ >= endJ ? Direction.LEFT : Direction.RIGHT;

            int verticalAmount = Math.abs(endI - startI);
            int horizontalAmount = Math.abs(endJ - startJ);

            Direction[] dirsVertFirst = new Direction[verticalAmount + horizontalAmount];
            for (int i = 0; i < verticalAmount; i++) {
                dirsVertFirst[i] = vertDir;
            }
            for (int i = verticalAmount; i < verticalAmount + horizontalAmount; i++) {
                dirsVertFirst[i] = horizDir;
            }
            Direction[] dirsHorizFirst = new Direction[verticalAmount + horizontalAmount];
            for (int i = 0; i < horizontalAmount; i++) {
                dirsHorizFirst[i] = horizDir;
            }
            for (int i = horizontalAmount; i < verticalAmount + horizontalAmount; i++) {
                dirsHorizFirst[i] = vertDir;
            }

            Collection<Direction[]> directions = new ArrayList<>();
            directions.add(dirsVertFirst);
            if (Arrays.hashCode(dirsVertFirst) != Arrays.hashCode(dirsHorizFirst)) {
                directions.add(dirsHorizFirst);
            }

//            Collection<Direction[]> directions = Permutations.uniquePermutations(dirsVertFirst);

            List<String> cacheStrings = new ArrayList<>(directions.size());
            for (Direction[] route : directions) {
                int i = startI;
                int j = startJ;
                boolean valid = true;
                for (Direction dir : route) {
                    i += dir.getI();
                    j += dir.getJ();
                    if (!validator.apply(i, j)) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    cacheStrings.add(toCacheString(route));
                }
            }
            return cacheStrings;
        }

        private static String toCacheString(final Direction[] route) {
            StringBuilder sb = new StringBuilder();
            for (Direction dir : route) {
                sb.append(getChar(dir));
            }
            return sb.toString();
        }

        private static char getChar(final Direction dir) {
            if (dir == Direction.UP) {
                return '^';
            }
            if (dir == Direction.RIGHT) {
                return '>';
            }
            if (dir == Direction.DOWN) {
                return 'v';
            }
            return '<';
        }
    }

}
