package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.parse.LoaderUtils;
import com.rev.puzzles.utils.dp.DynamicProgram;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.BiFunction;

public final class PE067 {

    @PeProblem(number = 67)
    @PeTestData(inputs = {"3", "7 4", "2 4 6", "8 5 9 3"}, solutions = {"23"})
    public long maxPathSumPartTwo(final ProblemResourceLoader<List<String>> inputs) {
        return maxPathSumPartTwo(inputs.resources());
    }

    private long maxPathSumPartTwo(final List<String> triangleLines) {

        final int[][] triangle = LoaderUtils.loadResourcesAsIntMatrix(
                triangleLines,
                line -> line.split(" ")
        );

        final MaxPathsDpFunction func = new MaxPathsDpFunction(triangle);

        final DynamicProgram<Pair<Integer, Integer>, Long> dp = new DynamicProgram<>(func);

        int depth = triangle.length - 1;
        for (int i = 0; i < triangle[depth].length; i++) {
            dp.compute(Pair.of(depth, i));
        }
        return func.longestRoute;
    }

    private static final class MaxPathsDpFunction implements BiFunction
            <DynamicProgram<Pair<Integer, Integer>, Long>, Pair<Integer, Integer>, Long> {
        private final int[][] triangle;
        private long longestRoute = 0;

        private MaxPathsDpFunction(final int[][] triangle) {
            this.triangle = triangle;
        }

        @Override
        public Long apply(final DynamicProgram<Pair<Integer, Integer>, Long> dp,
                          final Pair<Integer, Integer> coord) {
            final long routeLength = applyImpl(dp, coord);
            if (routeLength > longestRoute) {
                longestRoute = routeLength;
            }
            return routeLength;
        }

        private long applyImpl(final DynamicProgram<Pair<Integer, Integer>, Long> dp,
                               final Pair<Integer, Integer> coord) {
            final int depth = coord.getLeft();
            if (depth == 0) {
                return triangle[0][0];
            }

            final int width = coord.getRight();
            if (width == 0) {
                return triangle[depth][width] + dp.compute(Pair.of(depth - 1, width));
            }
            if (width == triangle[depth].length - 1) {
                return triangle[depth][width] + dp.compute(Pair.of(depth - 1, width - 1));
            }

            return Math.max(triangle[depth][width] + dp.compute(Pair.of(depth - 1, width - 1)),
                    triangle[depth][width] + dp.compute(Pair.of(depth - 1, width)));
        }
    }
}
