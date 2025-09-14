package com.rev.aoc.problems.y2016;

import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.framework.problem.ProblemExecutionException;

import java.util.List;

import com.rev.aoc.framework.problem.ResourceLoader;

public final class D02 extends AocProblem<Integer, String> {

    @AocProblemI(year = 2016, day = 2, part = 1)
    @Override
    public Integer partOneImpl(final ResourceLoader resourceLoader) {
        List<String> instructions = resourceLoader.resources();
        int[][] keypad = getKeypad();

        final StringBuilder result = new StringBuilder();

        int i = 1;
        int j = 1;

        for (final String instruction : instructions) {
            for (int k = 0; k < instruction.length(); k++) {
                switch (instruction.charAt(k)) {
                    case 'U':
                        j = Math.max(0, j - 1);
                        break;
                    case 'D':
                        j = Math.min(2, j + 1);
                        break;
                    case 'R':
                        i = Math.min(2, i + 1);
                        break;
                    case 'L':
                        i = Math.max(0, i - 1);
                        break;
                    default:
                        throw new ProblemExecutionException(
                                String.format("Unrecognised instruction %s", instruction.charAt(k))
                        );
                }
            }
            result.append(keypad[j][i]);
        }

        return Integer.parseInt(result.toString());
    }

    @AocProblemI(year = 2016, day = 2, part = 2)
    @Override
    public String partTwoImpl(final ResourceLoader resourceLoader) {
        List<String> instructions = resourceLoader.resources();
        char[][] keypad = getKeypadPart2();

        final StringBuilder result = new StringBuilder();

        int i = 0;
        int j = 2;

        for (final String instruction : instructions) {
            for (int k = 0; k < instruction.length(); k++) {
                int iStep = Math.abs(j - 2);
                int jStep = Math.abs(i - 2);
                switch (instruction.charAt(k)) {
                    case 'U':
                        j = Math.max(jStep, j - 1);
                        break;
                    case 'D':
                        j = Math.min(4 - jStep, j + 1);
                        break;
                    case 'R':
                        i = Math.min(4 - iStep, i + 1);
                        break;
                    case 'L':
                        i = Math.max(iStep, i - 1);
                        break;
                    default:
                        throw new ProblemExecutionException(
                                String.format("Unrecognised instruction %s", instruction.charAt(k))
                        );
                }
            }
            result.append(keypad[j][i]);
        }

        return result.toString();
    }

    private static int[][] getKeypad() {
        return new int[][]{
                new int[]{1, 2, 3},
                new int[]{4, 5, 6},
                new int[]{7, 8, 9}
        };
    }

    private static char[][] getKeypadPart2() {
        return new char[][]{
                new char[]{'0', '0', '1', '0', '0'},
                new char[]{'0', '2', '3', '4', '0'},
                new char[]{'5', '6', '7', '8', '9'},
                new char[]{'0', 'A', 'B', 'C', '0'},
                new char[]{'0', '0', 'D', '0', '0'}
        };
    }
}
