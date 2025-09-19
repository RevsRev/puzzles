package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class L383 {
    @LeetProblem(number = 383)
    public Boolean apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return canConstruct((String) problemResourceLoader.resources()[0],
                (String) problemResourceLoader.resources()[1]);
    }

    public boolean canConstruct(final String ransomNote, final String magazine) {
        final Map<Character, Integer> ransomCounts = getLetterCounts(ransomNote);
        final Map<Character, Integer> magazinesCounts = getLetterCounts(magazine);

        final Iterator<Character> it = ransomCounts.keySet().iterator();
        while (it.hasNext()) {
            final char c = it.next();
            final int val = ransomCounts.get(c);
            if (magazinesCounts.containsKey(c)) {
                if (magazinesCounts.get(c) < val) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private Map<Character, Integer> getLetterCounts(final String str) {
        if (str == null || str.length() == 0) {
            return new HashMap();
        }

        final Map<Character, Integer> retval = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            final char c = str.charAt(i);
            if (retval.get(c) == null) {
                retval.put(c, 0);
            }
            retval.put(c, retval.get(c) + 1);
        }
        return retval;
    }
}
