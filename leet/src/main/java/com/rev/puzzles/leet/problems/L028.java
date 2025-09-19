package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L028 {
    @LeetProblem(number = 28)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return strStr((String) problemResourceLoader.resources()[0], (String) problemResourceLoader.resources()[1]);
    }

    //Cheating solution
    public int strStr(final String haystack, final String needle) {
        return haystack.indexOf(needle);
    }

    //sort of not cheating
    public int sortOfNotCheating(final String haystack, final String needle) {
        if (needle.length() > haystack.length()) {
            return -1;
        }
        for (int i = 0, j = needle.length(); j <= haystack.length(); i++, j++) {
            if (haystack.substring(i, j).equals(needle)) {
                return i;
            }
        }
        return -1;
    }

    //least cheating would be put into char arrays and iterate through... cba to do that.
}
