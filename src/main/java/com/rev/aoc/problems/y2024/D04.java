package com.rev.aoc.problems.y2024;

import com.rev.aoc.AocCoordinate;
import com.rev.aoc.problems.AocProblem;

import java.util.List;

public final class D04 extends AocProblem {

    private static final char[] XMAS = {'X', 'M', 'A', 'S'};
    private static final int[][] XMAS_STEPS =  new int[][]{
            {1, 0},
            {1, 1},
            {0, 1},
            {-1, 1},
            {-1, 0},
            {-1, -1},
            {0, -1},
            {1, -1}};
    private static final int[][] SQUARE_CORNERS = new int[][] {
            {-1, 1}, {1, 1}, {1, -1}, {-1, -1}
    };

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 4);
    }

    @Override
    protected long partOneImpl() {
        char[][] wordSearch = loadWordSearch();
        return countXmas(wordSearch);
    }

    @Override
    protected long partTwoImpl() {
        char[][] wordSearch = loadWordSearch();
        return countXmasSquares(wordSearch);
    }

    private long countXmas(final char[][] wordSearch) {
        int height = wordSearch.length;
        int width = wordSearch[0].length;

        long count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                count += xmasMatches(wordSearch, height, width, i, j);
            }
        }
        return count;
    }

    private long countXmasSquares(final char[][] wordSearch) {
        int height = wordSearch.length;
        int width = wordSearch[0].length;

        long count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (xmasSquareMatches(wordSearch, height, width, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean xmasSquareMatches(final char[][] wordSearch,
                             final int height, final int width, final int i, final int j) {
        if (wordSearch[i][j] != 'A') {
            return false;
        }
        if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
            return false;
        }
        for (int cIndex = 0; cIndex < 2; cIndex++) {
            int[] thisCorner = SQUARE_CORNERS[cIndex];
            int[] nextCorner = SQUARE_CORNERS[cIndex + 1];
            char c = wordSearch[i + thisCorner[0]][j + thisCorner[1]];
            if (c == 'X' || c == 'A') {
                continue;
            }

            if (c != wordSearch[i + nextCorner[0]][j + nextCorner[1]]) {
                continue;
            }

            char opposite = c == 'M' ? 'S' : 'M';
            if (opposite != wordSearch[i - thisCorner[0]][j - thisCorner[1]]) {
                continue;
            }
            if (opposite != wordSearch[i - nextCorner[0]][j - nextCorner[1]]) {
                continue;
            }
            return true;
        }
        return false;
    }

    private long xmasMatches(final char[][] wordSearch,
                             final int height, final int width, final int i, final int j) {
        long count = 0;
        for (int[] step : XMAS_STEPS) {
            if (xmasMatches(wordSearch, height, width, i, j, step)) {
                count++;
            }
        }
        return count;
    }

    private boolean xmasMatches(final char[][] wordSearch,
                                final int height, final int width, final int i, final int j, final int[] step) {
        int index = 0;
        int x = i;
        int y = j;
        while (Math.abs(x - i) <= 3
                && x >= 0
                && x < height
                && Math.abs(y - j) <= 3
                && y >= 0
                && y < width) {
            if (wordSearch[x][y] != XMAS[index]) {
                return false;
            }
            x += step[0];
            y += step[1];
            index += 1;
            if (index == XMAS.length) {
                return true;
            }
        }
        return false;
    }

    private char[][] loadWordSearch() {
        List<String> lines = loadResources();
        char[][] wordSearch = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            wordSearch[i] = line.toCharArray();
        }
        return wordSearch;
    }
}
