package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Set;

import com.rev.aoc.framework.problem.ResourceLoader;

public final class D03 extends AocProblem<Long, Long> {

    private static final char UP = '^';
    private static final char DOWN = 'v';
    private static final char LEFT = '<';
    private static final char RIGHT = '>';
    public static final int BASEMENT = -1;

    @Override
    @AocProblemI(year = 2015, day = 3, part = 1)
    protected Long partOneImpl(final ResourceLoader resourceLoader) {
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

    @Override
    @AocProblemI(year = 2015, day = 3, part = 2)
    protected Long partTwoImpl(final ResourceLoader resourceLoader) {
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
