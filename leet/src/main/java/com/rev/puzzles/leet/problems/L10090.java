package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class L10090 {
    @LeetProblem(number = 10090)
    public List<List<String>> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return subsetsWithDup((String[]) problemResourceLoader.resources()[0]);
    }

    public List<List<String>> subsetsWithDup(final String[] strs) {
        groupStrings(strs);
        final List<List<String>> solution = new ArrayList<>();
        final List<String> empty = new ArrayList<>();
        subsetsWithDup(solution, empty, strs, 0);
        return solution;
    }

    private void subsetsWithDup(final List<List<String>> solution, final List<String> current, final String[] strs,
                                final int index) {
        solution.add(current);
        for (int i = index; i < strs.length; i++) {
            if (i == index || !strs[i].equals(strs[i - 1])) {
                final List<String> copy = new ArrayList<>(current);
                copy.add(strs[i]);
                subsetsWithDup(solution, copy, strs, i + 1);
            }
        }
    }

    private void groupStrings(final String[] strs) {
        final Map<String, Integer> counts = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            final int count = counts.computeIfAbsent(strs[i], k -> 0);
            counts.put(strs[i], count + 1);
        }

        int start = 0;
        final Iterator<String> it = counts.keySet().iterator();
        while (it.hasNext()) {
            final String str = it.next();
            final int count = counts.get(str);
            final int end = start + count;
            for (int i = start; i < end; i++) {
                strs[i] = str;
            }
            start = end;
        }
    }
}
