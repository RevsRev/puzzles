package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public final class L032 {
    @LeetProblem(number = 32)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return longestValidParentheses((String) problemResourceLoader.resources()[0]);
    }

    public int longestValidParentheses(final String s) {

        final Map<Integer, Integer> found = new HashMap<>();
        int longest = 0;

        final int len = s.length();
        int i = 0;
        while (i < len - 1) {
            if (s.charAt(i) != '(' || s.charAt(i + 1) != ')') {
                i++;
            } else {
                final int right = expand(i, i + 1, found, s);
                final int left = found.get(right);
                final int runLength = right - left + 1;
                if (runLength > longest) {
                    longest = runLength;
                }
                i = right + 1;
            }
        }
        return longest;
    }

    private int expand(int left, int right, final Map<Integer, Integer> found, final String s) {
        while (left > 0 & right < s.length() - 1 && s.charAt(left - 1) == '(' && s.charAt(right + 1) == ')') {
            left--;
            right++;
        }
        if (found.containsKey(left - 1)) {
            left = found.remove(left - 1);
            found.put(right, left);
            return expand(left, right, found, s);
        }
        found.put(right, left);
        return right;
    }


    //Faster solution using stack
    public int longestValidParenthesisUsingStack(final String s) {
        final Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            final Character ch = s.charAt(i);
            if (ch == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                }
                max = Math.max(max, i - stack.peek());
            }
        }
        return max;
    }


}
