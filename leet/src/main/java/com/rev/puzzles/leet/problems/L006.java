package com.rev.puzzles.leet.problems;


import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.List;

public final class L006 {
    @LeetProblem(number = 6)
    public String apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return convert((String) problemResourceLoader.resources()[0], (int) problemResourceLoader.resources()[1]);
    }

    public String convert(final String s, final int numRows) {

        if (numRows == 1) {
            return s;
        }

        final int l = 2 * numRows - 2;
        final List<ArrayList<Character>> rows = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            rows.add(new ArrayList<>());
        }

        for (int i = 0; i < s.length(); i++) {
            final int blockIndex = i % l;
            if (blockIndex < numRows) {
                rows.get(blockIndex).add(s.charAt(i));
            } else {
                final int index = numRows - (blockIndex - numRows + 2);
                rows.get(index).add(s.charAt(i));
            }
        }

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            final List<Character> line = rows.get(i);
            for (int j = 0; j < line.size(); j++) {
                sb.append(line.get(j));
            }
        }
        return sb.toString();
    }
}
