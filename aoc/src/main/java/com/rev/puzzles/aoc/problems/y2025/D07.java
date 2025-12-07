package com.rev.puzzles.aoc.problems.y2025;

import com.rev.puzzles.algo.dp.DynamicProgram;
import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;
import com.rev.puzzles.parse.LoaderUtils;
import org.jgrapht.alg.util.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.BiFunction;

public final class D07 {

    @AocProblemI(year = 2025, day = 7, part = 1)
    public long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {

        final char[][] chars = LoaderUtils.loadResourcesAsCharMatrix(resourceLoader.resources());

        final Pair<Integer, Integer> start = getStart(chars);

        final Set<Pair<Integer, Integer>> splitters = new HashSet<>();
        final Set<Pair<Integer, Integer>> visited = new HashSet<>();
        final Stack<Pair<Integer, Integer>> tachyons = new Stack<>();

        tachyons.add(Pair.of(start.getFirst() + 1, start.getSecond()));

        while (!tachyons.isEmpty()) {
            final Pair<Integer, Integer> tachyon = tachyons.pop();
            if (tachyon.getFirst() == chars.length - 1) {
                continue;
            }

            final Pair<Integer, Integer> nextCoordinate = Pair.of(tachyon.getFirst() + 1, tachyon.getSecond());
            if (visited.contains(nextCoordinate)) {
                continue;
            } else {
                visited.add(nextCoordinate);
            }
            final char nextChar = chars[tachyon.getFirst() + 1][tachyon.getSecond()];

            if (nextChar == '.') {
                tachyons.add(nextCoordinate);
                continue;
            }

            if (nextChar == '^') {
                splitters.add(nextCoordinate);
                final Pair<Integer, Integer> right = Pair.of(tachyon.getFirst() + 1, tachyon.getSecond() + 1);
                final Pair<Integer, Integer> left = Pair.of(tachyon.getFirst() + 1, tachyon.getSecond() - 1);

                if (right.getFirst() <= chars[0].length - 1) {
                    tachyons.add(right);
                }
                if (left.getSecond() >= 0) {
                    tachyons.add(left);
                }
            }
        }

        return splitters.size();
    }

    @AocProblemI(year = 2025, day = 7, part = 2)
    public long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        final char[][] chars = LoaderUtils.loadResourcesAsCharMatrix(resourceLoader.resources());

        final TimelinesDynamicProblemFunc timelinesDynamicProblemFunc = new TimelinesDynamicProblemFunc(chars);
        DynamicProgram<Pair<Integer, Integer>, Long> timelineDp = new DynamicProgram<>(timelinesDynamicProblemFunc);

        long timelines = 0;
        for (int j = 0; j < chars[0].length; j++) {
            timelines += timelineDp.compute(Pair.of(chars.length - 1, j));
        }
        return timelines;
    }

    private static final class TimelinesDynamicProblemFunc
            implements BiFunction<DynamicProgram<Pair<Integer, Integer>, Long>, Pair<Integer, Integer>, Long> {

        private final char[][] chars;

        TimelinesDynamicProblemFunc(final char[][] chars) {
            this.chars = chars;
        }

        @Override
        public Long apply(final DynamicProgram<Pair<Integer, Integer>, Long> thisDp,
                          final Pair<Integer, Integer> coordinate) {
            final Integer depth = coordinate.getFirst();
            final Integer width = coordinate.getSecond();

            if (depth == 0) {
                return chars[coordinate.getFirst()][coordinate.getSecond()] == 'S' ? 1L : 0;
            }

            long computed = 0;
            if (chars[depth - 1][width] == '.' || chars[depth - 1][width] == 'S') {
                final Pair<Integer, Integer> up = Pair.of(depth - 1, width);
                computed += thisDp.compute(up);
            }

            if (width != 0 && (chars[depth][width - 1] == '^' || chars[depth][width - 1] == 'S')) {
                final Pair<Integer, Integer> leftUp = Pair.of(depth - 1, width - 1);
                computed += thisDp.compute(leftUp);
            }

            if (width != chars[0].length - 1 && (chars[depth][width + 1] == '^' || chars[depth][width + 1] == 'S')) {
                final Pair<Integer, Integer> rightUp = Pair.of(depth - 1, width + 1);
                computed += thisDp.compute(rightUp);
            }
            return computed;
        }
    }

    private static Pair<Integer, Integer> getStart(final char[][] chars) {
        Pair<Integer, Integer> start = null;
        for (int i = 0; i < chars[0].length; i++) {
            if (chars[0][i] == 'S') {
                start = Pair.of(0, i);
                break;
            }
        }

        if (start == null) {
            throw new ProblemExecutionException("Could not find problem start");
        }
        return start;
    }
}
