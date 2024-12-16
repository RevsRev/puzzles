package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.io.load.LoaderUtils;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.util.geom.Direction;
import com.rev.aoc.util.geom.UnitCell;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Set;

public final class D12 extends AocProblem {
    private static final int[][] DIRS = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 12);
    }

    @Override
    protected long partOneImpl() {
        UnitCell<Character>[][] unitCells = getUnitCells();
        Set<UnitCell<Character>> visitedArea = new HashSet<>();
        Set<UnitCell<Character>> visitedPerimeter = new HashSet<>();

        long score = 0;
        for (int i = 0; i < unitCells.length; i++) {
            for (int j = 0; j < unitCells[0].length; j++) {
                long area = unitCells[i][j].area(visitedArea);
                long perimeter = unitCells[i][j].perimeter(visitedPerimeter);
                score += area * perimeter;
            }
        }
        return score;
    }


    @Override
    protected long partTwoImpl() {
        UnitCell<Character>[][] unitCells = getUnitCells();
        Set<UnitCell<Character>> visitedArea = new HashSet<>();
        Set<UnitCell<Character>> visitedEdgeCells = new HashSet<>();
        Set<Pair<Integer, Character>> visitedEdges = new HashSet<>();

        long score = 0;
        for (int i = 0; i < unitCells.length; i++) {
            for (int j = 0; j < unitCells[0].length; j++) {
                long area = unitCells[i][j].area(visitedArea);
                long edges = unitCells[i][j].edges(visitedEdges, visitedEdgeCells);
                score += area * edges;
            }
        }
        return score;
    }

    private UnitCell<Character>[][] getUnitCells() {
        char[][] plots = LoaderUtils.loadResourcesAsCharMatrix(loadResources());
        int height = plots.length;
        int width = plots[0].length;
        UnitCell<Character>[][] cells = new UnitCell[height][width];

        //initialise cells
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new UnitCell<>(i, j, plots[i][j]);
            }
        }
        //link cells
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < height; j++) {
                for (Direction dir : Direction.UP) {
                    int nextI = i + dir.getI();
                    int nextJ = j + dir.getJ();

                    if (nextI < 0 || nextI >= height || nextJ < 0 || nextJ >= width) {
                        continue;
                    }
                    UnitCell<Character> neighbour = cells[nextI][nextJ];
                    cells[i][j].addNeighbour(neighbour, dir);
                }
            }
        }

        //merge cells!
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j].mergeBorders();
            }
        }
        return cells;
    }

    // Old part one solution...
//    private Pair<Integer, Integer> calcPerimeterAndArea(final char[][] plots,
//                                                        final Set<Pair<Integer, Integer>> unvisited,
//                                                        final int width,
//                                                        final int height,
//                                                        final int i,
//                                                        final int j) {
//        unvisited.remove(Pair.of(i, j));
//        char c = plots[i][j];
//        int perimeter = 0;
//        int area = 1;
//        for (int dirIndex = 0; dirIndex < DIRS.length; dirIndex++) {
//            int nextI = i + DIRS[dirIndex][0];
//            int nextJ = j + DIRS[dirIndex][1];
//            if (nextI < 0 || nextI >= height || nextJ < 0 || nextJ >= width) {
//                perimeter++;
//                continue;
//            }
//            if (plots[nextI][nextJ] != c) {
//                perimeter++;
//            } else if (unvisited.contains(Pair.of(nextI, nextJ))) {
//                Pair<Integer, Integer> pAndA =
//                        calcPerimeterAndArea(plots, unvisited, width, height, nextI, nextJ);
//                perimeter += pAndA.getLeft();
//                area += pAndA.getRight();
//            }
//        }
//        return Pair.of(perimeter, area);
//    }
}
