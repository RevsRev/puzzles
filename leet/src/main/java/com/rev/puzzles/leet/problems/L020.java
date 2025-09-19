package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

@SuppressWarnings("checkstyle:FinalParameters")
public final class L020 {
    @LeetProblem(number = 20)
    public Boolean apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return isValid((String) problemResourceLoader.resources()[0]);
    }

    public boolean isValid(String s) {
        while (s.length() > 0) {
            final int size = s.length();
            for (int i = 0; i < s.length(); i++) {
                while (i < s.length() - 1 && match(s.charAt(i), s.charAt(i + 1))) {
                    if (i < s.length() - 1) {
                        s = s.substring(0, i) + s.substring(i + 2);
                    } else {
                        s = s.substring(0, i);
                    }
                }
            }
            if (size == s.length()) {
                break;
            }
        }
        return s.length() == 0;
    }

    private boolean match(final char a, final char b) {
        return (a == '(' && b == ')') || (a == '[' && b == ']') || (a == '{' && b == '}');
    }

    //Alternatives - use a stack
    //use str.replace to replace (), {} && []

}
