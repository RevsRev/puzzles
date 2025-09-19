package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class L049 {
    @LeetProblem(number = 49)
    public List<List<String>> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return groupAnagrams((String[]) problemResourceLoader.resources()[0]);
    }

    public List<List<String>> groupAnagrams(final String[] strs) {
        final Map<Integer, List<String>> bins = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            final char[] strChars = strs[i].toCharArray();
            Arrays.sort(strChars);
            final int hash = Arrays.hashCode(strChars);
            bins.computeIfAbsent(hash, k -> new ArrayList<>()).add(strs[i]);
        }
        final List<List<String>> retval = new ArrayList<>();
        retval.addAll(bins.values());
        return retval;
    }
}
