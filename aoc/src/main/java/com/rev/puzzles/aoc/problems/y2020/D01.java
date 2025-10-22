package com.rev.puzzles.aoc.problems.y2020;

import com.rev.puzzles.algo.arr.ArraySums;
import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;
import com.rev.puzzles.parse.LoaderUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public final class D01 {

    public static final int TARGET = 2020;

    @AocProblemI(year = 2020, day = 1, part = 1)
    public Long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        final long[] longs = LoaderUtils.linesToLongArray(resourceLoader.resources());
        final Optional<Pair<Integer, Integer>> result = ArraySums.findElementsSummingTo(longs, TARGET);

        if (result.isEmpty()) {
            throw new ProblemExecutionException("No solution found");
        }

        return longs[result.get().getLeft()] * longs[result.get().getRight()];
    }

}
