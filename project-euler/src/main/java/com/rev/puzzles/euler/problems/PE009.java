package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.ntheory.primes.Factors;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public final class PE009 {

    @PeProblem(number = 9)
    @PeTestData(inputs = {"12"}, solutions = {"60"})
    public long specialPythagoreanTriplet(final ProblemResourceLoader<List<String>> inputs) {
        return specialPythagoreanTriplet(Integer.parseInt(inputs.resources().get(0)));
    }

    /*

    a ^ 2 + b ^ 2 = c ^ 2
    parameterise with a = x ^ 2 - y ^ 2, b = 2xy, c = x ^2 + y ^ 2
    +ve solutions => a > 0 => x > y
    a + b + c = 2x(x+y)
     */

    private long specialPythagoreanTriplet(final int n) {

        if (n % 2 == 1) {
            throw new RuntimeException("No solution exists!");
        }

        final Factors factors = Factors.create();
        final List<Pair<Long, Long>> pairs = factors.pairwiseFactors(n / 2);

        final List<Pair<Long, Long>> xyCandidates = new ArrayList<>();

        pairs.forEach(pair -> {
            final long x = pair.getLeft();
            final long xPlusY = pair.getRight();
            final long y = xPlusY - x;

            if (x > y) {
                xyCandidates.add(Pair.of(x, y));
            }
        });

        if (xyCandidates.size() != 1) {
            throw new RuntimeException("Either no or more than one solution exists, and we haven't handled this case.");
        }

        final Pair<Long, Long> xyPair = xyCandidates.get(0);
        final long x = xyPair.getLeft();
        final long y = xyPair.getRight();

        final long a = x * x - y * y;
        final long b = 2 * x * y;
        final long c = x * x + y * y;

        return a * b * c;
    }

}
