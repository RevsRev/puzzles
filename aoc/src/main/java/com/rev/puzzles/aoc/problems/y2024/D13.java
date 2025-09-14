package com.rev.puzzles.aoc.problems.y2024;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.problem.ResourceLoader;
import com.rev.puzzles.framework.util.math.linalg.matrix.Mat2;
import com.rev.puzzles.framework.util.math.linalg.vec.Vec2;
import com.rev.puzzles.framework.util.math.ntheory.eq.SimultaneousSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class D13 {

    public static final int PART_ONE_LIMIT = 100;
    public static final double EPSILON = 0.0001;
    public static final long PART_TWO_ERROR = 10000000000000L;

    @AocProblemI(year = 2024, day = 13, part = 1)
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        List<SimultaneousSolver> solvers = loadSolvers(resourceLoader, false);
        return computeCost(solvers, D13::validResultPartOne);
    }

    @AocProblemI(year = 2024, day = 13, part = 2)
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        List<SimultaneousSolver> solvers = loadSolvers(resourceLoader, true);
        return computeCost(solvers, D13::validResultPartTwo);
    }

    private long computeCost(final List<SimultaneousSolver> solvers, final Function<Double, Boolean> validator) {
        long cost = 0;
        for (SimultaneousSolver solver : solvers) {
            if (solver.canSolve()) {
                Vec2 result = solver.solve();
                if (!validator.apply(result.getX()) || !validator.apply(result.getY())) {
                    continue;
                }
                cost += 3 * Math.round(result.getX()) + Math.round(result.getY());
            }
        }
        return cost;
    }

    private static boolean validResultPartOne(double x) {
        return x >= 0 && x <= PART_ONE_LIMIT && isInteger(x);
    }

    private static boolean validResultPartTwo(double x) {
        return x >= 0 && isInteger(x);
    }

    private static boolean isInteger(double x) {
        return Math.abs(x - Math.round(x)) < EPSILON;
    }

    private List<SimultaneousSolver> loadSolvers(final ResourceLoader resourceLoader, boolean partTwo) {
        List<String> lines = resourceLoader.resources();
        long amountExtra = partTwo ? PART_TWO_ERROR : 0;
        List<SimultaneousSolver> retval = new ArrayList<>(lines.size() / 3);
        for (int i = 0; i < lines.size(); i += 4) {
            String[] aStr = lines.get(i)
                    .replaceAll("\\s+", "")
                    .replaceAll("ButtonA:", "")
                    .replaceAll(".\\+", "")
                    .trim()
                    .split(",");
            String[] bStr = lines.get(i + 1)
                    .replaceAll("\\s+", "")
                    .replaceAll("ButtonB:", "")
                    .replaceAll(".\\+", "")
                    .trim()
                    .split(",");
            String[] cStr = lines.get(i + 2)
                    .replaceAll("\\s+", "")
                    .replaceAll("Prize:", "")
                    .replaceAll(".=", "")
                    .trim()
                    .split(",");
            Mat2 lhs = new Mat2(Double.parseDouble(aStr[0]),
                    Double.parseDouble(bStr[0]),
                    Double.parseDouble(aStr[1]),
                    Double.parseDouble(bStr[1]));
            Vec2 rhs = new Vec2(
                    Double.parseDouble(cStr[0]) + amountExtra,
                    Double.parseDouble(cStr[1]) + amountExtra);
            retval.add(new SimultaneousSolver(lhs, rhs));
        }
        return retval;
    }

}
