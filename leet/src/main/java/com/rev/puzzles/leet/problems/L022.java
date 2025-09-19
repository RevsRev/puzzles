package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class L022 {
    @LeetProblem(number = 22)
    public List<String> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return generateParenthesis((Integer) problemResourceLoader.resources()[0]);
    }

    public List<String> generateParenthesis(final int n) {
        return generateParenthesisSet(n).stream().toList();
    }

    private Set<String> generateParenthesisSet(final int n) {
        final Map<Integer, Set<String>> cache = new HashMap<>();
        return generateParenthesisSet(cache, n);
    }

    private Set<String> generateParenthesisSet(final Map<Integer, Set<String>> cache, final int n) {

        if (cache.containsKey(n)) {
            return cache.get(n);
        }

        if (n == 1) {
            cache.put(1, Set.of("()"));
            return cache.get(n);
        }

        final Set<String> result = new HashSet<>();

        final Set<String> enclosed = generateParenthesisSet(cache, n - 1);
        final Iterator<String> it = enclosed.iterator();
        while (it.hasNext()) {
            result.add("(" + it.next() + ")");
        }

        for (int i = 1; i < n; i++) {
            final Set<String> left = generateParenthesisSet(cache, i);
            final Set<String> right = generateParenthesisSet(cache, n - i);
            final Iterator<String> lIt = left.iterator();
            while (lIt.hasNext()) {
                final String l = lIt.next();
                final Iterator<String> rIt = right.iterator();
                while (rIt.hasNext()) {
                    result.add(l + rIt.next());
                }
            }
        }
        cache.put(n, result);
        return result;
    }
}
