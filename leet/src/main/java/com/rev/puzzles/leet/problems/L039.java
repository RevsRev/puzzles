package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class L039 {
    @LeetProblem(number = 39)
    public List<List<Integer>> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return combinationSum((int[]) problemResourceLoader.resources()[0], (int) problemResourceLoader.resources()[1]);
    }

    public List<List<Integer>> combinationSum(final int[] candidates, final int target) {
        final Map<Integer, Set<Map<Integer, Integer>>> allSolutions = new HashMap<>();
        allSolutions.put(0, new HashSet<>());
        combinationSum(candidates, target, allSolutions);
        return convertSolutionsSetToList(allSolutions.get(target));
    }

    private List<List<Integer>> convertSolutionsSetToList(final Set<Map<Integer, Integer>> allSolutions) {
        final List<List<Integer>> retval = new ArrayList<>();
        for (final Map<Integer, Integer> solution : allSolutions) {
            retval.add(solutionAsList(solution));
        }
        return retval;
    }

    private List<Integer> solutionAsList(final Map<Integer, Integer> solution) {
        final List<Integer> retval = new ArrayList<>();
        for (final int x : solution.keySet()) {
            for (int i = 0; i < solution.get(x); i++) {
                retval.add(x);
            }
        }
        return retval;
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
