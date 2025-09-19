package com.rev.puzzles.aoc.problems.y2015;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class D03 {

    public static final int BASEMENT = -1;
    private static final char UP = '^';
    private static final char DOWN = 'v';
    private static final char LEFT = '<';
    private static final char RIGHT = '>';

    @AocProblemI(year = 2015, day = 3, part = 1)
    public Long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        String input = resourceLoader.resources().get(0);
        long x = 0;
        long y = 0;
        final Set<Pair<Long, Long>> visited = new HashSet<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            visited.add(Pair.of(x, y));
            if (c == UP) {
                y++;
            } else if (c == DOWN) {
                y--;
            } else if (c == RIGHT) {
                x++;
            } else {
                x--;
            }
        }
        return (long) visited.size();
    }

    @AocProblemI(year = 2015, day = 3, part = 2)
    public Long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        String input = resourceLoader.resources().get(0);
        long x = 0;
        long y = 0;
        final Set<Pair<Long, Long>> visited = new HashSet<>();
        for (int i = 0; i < input.length(); i += 2) {
            char c = input.charAt(i);
            visited.add(Pair.of(x, y));
            if (c == UP) {
                y++;
            } else if (c == DOWN) {
                y--;
            } else if (c == RIGHT) {
                x++;
            } else {
                x--;
            }
        }

        x = 0;
        y = 0;
        for (int i = 1; i < input.length(); i += 2) {
            char c = input.charAt(i);
            visited.add(Pair.of(x, y));
            if (c == UP) {
                y++;
            } else if (c == DOWN) {
                y--;
            } else if (c == RIGHT) {
                x++;
            } else {
                x--;
            }
        }
        return (long) visited.size();
    }
}
