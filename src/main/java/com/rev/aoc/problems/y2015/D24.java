package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.aoc.AocProblemI;
import com.rev.aoc.framework.problem.ResourceLoader;
import com.rev.aoc.util.arr.ArrayUtils;
import com.rev.aoc.util.search.BinarySolutionSearch;
import com.rev.aoc.util.set.SetUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class D24 {

    @AocProblemI(year = 2015, day = 24, part = 1)
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        final Long[] packages = loadPackages(resourceLoader);
        long target = ArrayUtils.sum(packages) / 3;
        final SetUtils.Constraint<Long> constraint = new SetUtils.Constraint<>(
                (i, arr) -> ArrayUtils.sum(arr, i) > target,
                (i, arr) -> ArrayUtils.sum(arr, i) == target);
        List<Long[]> candidates = SetUtils.constrainedSetsOfSizeLeqN(
                packages,
                Arrays.copyOf(packages, packages.length / 3),
                constraint);

        candidates.sort(Comparator.comparingInt(a -> a.length));
        final int searchEnd = BinarySolutionSearch.search(
                0,
                candidates.size(),
                index -> candidates.get(index).length > candidates.get(0).length);

        long minQuantumEntanglement = Long.MAX_VALUE;
        for (int i = 0; i < searchEnd; i++) {
            final long quantumEntanglement = ArrayUtils.mult(candidates.get(i));
            if (quantumEntanglement >= minQuantumEntanglement) {
                continue;
            }
            Long[] remaining = SetUtils.copyRemoveAll(packages, candidates.get(i));
            List<Long[]> middles = SetUtils.constrainedSetsOfSizeLeqN(
                    remaining,
                    Arrays.copyOf(remaining, remaining.length / 2),
                    constraint);
            for (int j = 0; j < middles.size(); j++) {
                Long[] end = SetUtils.copyRemoveAll(remaining, middles.get(j));
                if (ArrayUtils.sum(end) == target) {
                    minQuantumEntanglement = quantumEntanglement;
                }
            }
        }
        return minQuantumEntanglement;
    }

    @AocProblemI(year = 2015, day = 24, part = 2)
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        final Long[] packages = loadPackages(resourceLoader);
        long target = ArrayUtils.sum(packages) / 4;
        final SetUtils.Constraint<Long> constraint = new SetUtils.Constraint<>(
                (i, arr) -> ArrayUtils.sum(arr, i) > target,
                (i, arr) -> ArrayUtils.sum(arr, i) == target);
        List<Long[]> candidates = SetUtils.constrainedSetsOfSizeLeqN(
                packages,
                Arrays.copyOf(packages, packages.length / 4),
                constraint);

        candidates.sort(Comparator.comparingInt(a -> a.length));
        final int searchEnd = BinarySolutionSearch.search(
                0,
                candidates.size(),
                index -> candidates.get(index).length > candidates.get(0).length);

        long minQuantumEntanglement = Long.MAX_VALUE;
        for (int i = 0; i < searchEnd; i++) {
            final long quantumEntanglement = ArrayUtils.mult(candidates.get(i));
            if (quantumEntanglement >= minQuantumEntanglement) {
                continue;
            }
            Long[] remaining = SetUtils.copyRemoveAll(packages, candidates.get(i));
            List<Long[]> leftMiddles = SetUtils.constrainedSetsOfSizeLeqN(
                    remaining,
                    Arrays.copyOf(remaining, remaining.length / 3),
                    constraint);
            for (int j = 0; j < leftMiddles.size(); j++) {
                Long[] remainingLeft = SetUtils.copyRemoveAll(remaining, leftMiddles.get(j));
                List<Long[]> rightMiddles = SetUtils.constrainedSetsOfSizeLeqN(
                        remainingLeft,
                        Arrays.copyOf(remainingLeft, remaining.length / 2),
                        constraint);
                for (int k = 0; k < rightMiddles.size(); k++) {
                    Long[] end = SetUtils.copyRemoveAll(remainingLeft, rightMiddles.get(k));
                    if (ArrayUtils.sum(end) == target) {
                        minQuantumEntanglement = quantumEntanglement;
                    }
                }
            }
        }
        return minQuantumEntanglement;
    }

    private Long[] loadPackages(final ResourceLoader resourceLoader) {
        List<String> lines = resourceLoader.resources();
        Long[] packages = new Long[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            packages[i] = Long.parseLong(lines.get(i));
        }
        return packages;
    }
}
