package com.rev.puzzles.framework.util.grid;

public final class Grid {

    private Grid() {
    }

    public static char[][] stateComputer(final char[][] g, final GridUpdater gridUpdater, final int iterations) {
        char[][] grid = g;
        char[][] nextGrid = new char[grid.length][grid[0].length];
        for (int i = 0; i < iterations; i++) {
            updateGrid(grid, nextGrid, gridUpdater);

            final char[][] intermediate = grid;
            grid = nextGrid;
            nextGrid = intermediate;
        }
        return grid;
    }

    private static void updateGrid(final char[][] grid, final char[][] nextGrid, final GridUpdater gridUpdater) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                nextGrid[i][j] = gridUpdater.apply(i, j, grid);
            }
        }
    }

    public static long countGrid(final char[][] grid, final char match) {
        long count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == match) {
                    count++;
                }
            }
        }
        return count;
    }

    public interface GridUpdater {

        char apply(int i, int j, char[][] grid);
    }

}
