package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L058 {
    @LeetProblem(number = 58)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return lengthOfLastWord((String) problemResourceLoader.resources()[0]);
    }

    public int lengthOfLastWord(final String s) {
        int end = s.length() - 1;
        while (end >= 0 && s.charAt(end) == ' ') {
            end--;
        }
        int start = end;
        while (start >= 0 && s.charAt(start) != ' ') {
            start--;
        }
        return end - start;
    }
}
