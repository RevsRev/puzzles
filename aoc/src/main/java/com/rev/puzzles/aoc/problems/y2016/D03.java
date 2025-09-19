package com.rev.puzzles.aoc.problems.y2016;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.util.geom.Triangle;

import java.util.List;

import com.rev.puzzles.framework.framework.ResourceLoader;

public final class D03 {

    @AocProblemI(year = 2016, day = 3, part = 1)
    public Integer partOneImpl(final ResourceLoader resourceLoader) {
        int count = 0;
        List<String> maybeTriangles = resourceLoader.resources();
        for (final String maybeTriangle : maybeTriangles) {
            String[] sides = maybeTriangle.trim().split("\\s+");
            final long a = Long.parseLong(sides[0]);
            final long b = Long.parseLong(sides[1]);
            final long c = Long.parseLong(sides[2]);
            if (Triangle.validTriangle(a, b, c)) {
                count++;
            }
        }
        return count;
    }

    @AocProblemI(year = 2016, day = 3, part = 2)
    public Integer partTwoImpl(final ResourceLoader resourceLoader) {
        int count = 0;
        List<String> maybeTriangles = resourceLoader.resources();
        for (int i = 0; i < maybeTriangles.size(); i += 3) {
            final String first = maybeTriangles.get(i);
            final String second = maybeTriangles.get(i + 1);
            final String third = maybeTriangles.get(i + 2);

            String[] as = first.trim().split("\\s+");
            final long a1 = Long.parseLong(as[0]);
            final long a2 = Long.parseLong(as[1]);
            final long a3 = Long.parseLong(as[2]);

            String[] bs = second.trim().split("\\s+");
            final long b1 = Long.parseLong(bs[0]);
            final long b2 = Long.parseLong(bs[1]);
            final long b3 = Long.parseLong(bs[2]);

            String[] cs = third.trim().split("\\s+");
            final long c1 = Long.parseLong(cs[0]);
            final long c2 = Long.parseLong(cs[1]);
            final long c3 = Long.parseLong(cs[2]);

            if (Triangle.validTriangle(a1, b1, c1)) {
                count++;
            }
            if (Triangle.validTriangle(a2, b2, c2)) {
                count++;
            }
            if (Triangle.validTriangle(a3, b3, c3)) {
                count++;
            }
        }
        return count;
    }
}
