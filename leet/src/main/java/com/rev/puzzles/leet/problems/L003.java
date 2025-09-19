package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.HashMap;
import java.util.Map;

public final class L003 {
    @LeetProblem(number = 3)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return lengthOfLongestSubstring((String) problemResourceLoader.resources()[0]);
    }

    public int lengthOfLongestSubstring(final String s) {
        Map<Character, Integer> charMap = new HashMap<>();
        int runLength = 0;
        int longestRun = 0;
        int index = 0;
        while (index < s.length()) {
            final char c = s.charAt(index);
            if (charMap.containsKey(c)) {
                if (runLength > longestRun) {
                    longestRun = runLength;
                }
                index = charMap.get(c) + 1;
                charMap = new HashMap<>();
                runLength = 0;
            } else {
                charMap.put(c, index);
                runLength += 1;
                index += 1;
            }
        }

        if (runLength > longestRun) {
            longestRun = runLength;
        }

        return longestRun;
    }
}
