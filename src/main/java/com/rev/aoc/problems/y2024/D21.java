package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.util.geom.Direction;
import com.rev.aoc.util.math.perm.Permutations;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public final class D21 extends AocProblem {

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
    private static final int PART_TWO_DEPTH = 27;

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 21);
    }

    @Override
    protected long partOneImpl() {
        List<String> codes = loadResources();
        long totalComplexity = 0;
        for (String code : codes) {
            String s = solve(code, PART_ONE_DEPTH);
            totalComplexity += getNumeric(code) * s.length();
        }
        return totalComplexity;
    }

    @Override
    protected long partTwoImpl() {
        List<String> codes = loadResources();
        long totalComplexity = 0;
//        for (String code : codes) {
//            String s = solve(code, PART_TWO_DEPTH);
//            totalComplexity += getNumeric(code) * s.length();
//        }
        return totalComplexity;
    }

    public long getNumeric(final String s) {
        return Long.parseLong(s.replaceAll("[^\\d.]", ""));
    }

    public String solve(final String target, int maxDepth) {
        List<String> allSolutions = solve(target, maxDepth, 0);
        String minimumSolution = null;
        long minSolutionLength = Long.MAX_VALUE;
        for (String solution : allSolutions) {
            if (solution.length() < minSolutionLength) {
                minimumSolution = solution;
                minSolutionLength = solution.length();
            }
        }
        return minimumSolution;
    }

    private static final Map<Triple<String, Integer, Integer>, List<String>> SOLUTION_CACHE = new HashMap();

    public List<String> solve(final String target, final int maxDepth, final int depth) {
//        System.out.println(depth);
        Triple<String, Integer, Integer> key = Triple.of(target, maxDepth, depth);
        if (SOLUTION_CACHE.containsKey(key)) {
            return SOLUTION_CACHE.get(key);
        }

        if (depth == maxDepth) {
            SOLUTION_CACHE.put(key, List.of(target));
            return SOLUTION_CACHE.get(key);
        }

        final KeypadOperator keypadOperator = depth == 0 ? DOOR_KEYPAD : ROBOT_KEYPAD;
        char startChar = 'A';
        List<List<String>> combinations = new ArrayList<>(target.length());
        for (int i = 0; i < target.length(); i++) {
            char endChar = target.charAt(i);
            List<String> outboundOptions = keypadOperator.cache.get(startChar).get(endChar);
            List<String> optionsWithA = new ArrayList<>();
            for (String option : outboundOptions) {
                optionsWithA.add(option + 'A');
            }
            combinations.add(optionsWithA);
            startChar = endChar;
        }

//        long minSolutionLength = Long.MAX_VALUE;
        Set<String> uniqueSolutions = new HashSet<>();
        List<List<String>> successfulInputs = Permutations.getCombinations(combinations);
        for (List<String> option : successfulInputs) {
            StringBuilder sb = new StringBuilder();
            for (String sequence : option) {
                sb.append(sequence);
            }
            List<String> solved = solve(sb.toString(), maxDepth, depth + 1);
            uniqueSolutions.addAll(solved);
        }
        SOLUTION_CACHE.put(key, uniqueSolutions.stream().toList());
        return SOLUTION_CACHE.get(key);
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
