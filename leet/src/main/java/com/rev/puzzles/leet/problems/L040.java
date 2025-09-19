package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class L040 {
    @LeetProblem(number = 40)
    public List<List<Integer>> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return combinationSum((int[]) problemResourceLoader.resources()[0], (int) problemResourceLoader.resources()[1]);
    }

    public List<List<Integer>> combinationSum(final int[] candidates, final int target) {
        final Map<Integer, Set<Map<Integer, Integer>>> allSolutions = new HashMap<>();
        allSolutions.put(0, new HashSet<>());
        combinationSum(candidates, target, allSolutions);
        final Map<Integer, Integer> candidatesCount = getCandidatesCount(candidates);
        return convertSolutionsSetToList(allSolutions.get(target), candidatesCount);
    }

    private Map<Integer, Integer> getCandidatesCount(final int[] candidates) {
        final Map<Integer, Integer> candidatesCount = new HashMap<>();
        for (int i = 0; i < candidates.length; i++) {
            final int count = candidatesCount.computeIfAbsent(candidates[i], k -> 0);
            candidatesCount.put(candidates[i], count + 1);
        }
        return candidatesCount;
    }

    private List<List<Integer>> convertSolutionsSetToList(final Set<Map<Integer, Integer>> allSolutions,
                                                          final Map<Integer, Integer> candidatesCount) {
        final List<List<Integer>> retval = new ArrayList<>();
        for (final Map<Integer, Integer> solution : allSolutions) {
            final Optional<List<Integer>> integers = solutionAsList(solution, candidatesCount);
            if (integers.isPresent()) {
                retval.add(integers.get());
            }

        }
        return retval;
    }

    private Optional<List<Integer>> solutionAsList(final Map<Integer, Integer> solution,
                                                   final Map<Integer, Integer> candidatesCount) {
        final List<Integer> retval = new ArrayList<>();
        for (final int x : solution.keySet()) {
            if (solution.get(x) > candidatesCount.get(x)) {
                return Optional.empty();
            }
            for (int j = 0; j < solution.get(x); j++) {
                retval.add(x);
            }
        }
        return Optional.of(retval);
    }

    public void combinationSum(final int[] candidates, final int target,
                               final Map<Integer, Set<Map<Integer, Integer>>> allSolutions) {
        if (target < 0) {
            return;
        }
        if (allSolutions.containsKey(target)) {
            return;
        }

        allSolutions.put(target, new HashSet<>());

        for (int i = 0; i < candidates.length; i++) {
            final int newTarget = target - candidates[i];
            combinationSum(candidates, newTarget, allSolutions);
            final Set<Map<Integer, Integer>> solutionsForTarget = allSolutions.get(newTarget);
            allSolutions.get(target).addAll(addCandidateToSolutions(target, candidates[i], solutionsForTarget));
        }
    }

    private Set<Map<Integer, Integer>> addCandidateToSolutions(final int target, final int candidate,
                                                               final Set<Map<Integer, Integer>> solutionsForTarget) {
        final Set<Map<Integer, Integer>> newSolutions = new HashSet<>();
        if (solutionsForTarget != null) {
            for (final Map<Integer, Integer> solutionForTarget : solutionsForTarget) {
                final Map<Integer, Integer> newSolution = new HashMap<>(solutionForTarget);
                final int count = newSolution.computeIfAbsent(candidate, k -> 0);
                newSolution.put(candidate, count + 1);
                newSolutions.add(newSolution);
            }
        }
        if (target == candidate) {
            final Map<Integer, Integer> candidateSolution = new HashMap<>();
            candidateSolution.put(candidate, 1);
            newSolutions.add(candidateSolution);
        }

        return newSolutions;
    }
}
