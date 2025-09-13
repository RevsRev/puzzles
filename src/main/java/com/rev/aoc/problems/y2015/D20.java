package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.framework.problem.ProblemExecutionException;
import com.rev.aoc.util.math.ntheory.primes.Factors;
import com.rev.aoc.util.math.ntheory.primes.SieveOfEratosthenes;

import java.util.List;

public final class D20 extends AocProblem<Long, Long> {

    //Costly initialization, so use the same sieve for both parts
    public static final int SIEVE_INITIAL_SEARCH = 1000;
    public static final int PART_TWO_PRESENTS_LIMIT = 50;
    private final SieveOfEratosthenes sieve = SieveOfEratosthenes.create(SIEVE_INITIAL_SEARCH);

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 20);
    }

    @AocProblemI(year = 2015, day = 20, part = 1)
    @Override
    //TODO - Parallelisation?
    protected Long partOneImpl() {
//        return partOneSlow();
        return partOneFast();
    }

    @AocProblemI(year = 2015, day = 20, part = 2)
    @Override
    protected Long partTwoImpl() {
//        return partTwoSlow();
        return partTwoFast();
    }

    private long partOneFast() {
        final long target = Long.parseLong(loadResources().get(0)) / 10;
        long[] cache = new long[(int) target + 1];

        for (int i = 1; i <= target; i++) {
            int j = i;
            while (j < target) {
                cache[j] += i;
                j += i;
            }
            if (cache[i] >= target) {
                return i;
            }
        }
        throw new ProblemExecutionException("Solution not found");
    }

    private long partTwoFast() {
        final long target = (long) (Long.parseLong(loadResources().get(0)) / 11d) + 1;
        long[] cache = new long[(int) target + 1];

        for (int i = 1; i <= target; i++) {
            int j = i;
            int count = 0;
            while (j < target && count < PART_TWO_PRESENTS_LIMIT) {
                cache[j] += i;
                j += i;
                count++;
            }
            if (cache[i] >= target) {
                return i;
            }
        }
        throw new ProblemExecutionException("Solution not found");
    }

    private long partOneSlow() {
        final long target = Long.parseLong(loadResources().get(0)) / 10;
        //TODO - There is a much better bound for this problem, but we need to write an inequality solver to use it.
//        final long searchStart = (long) Math.sqrt( ( (Math.log(target) / Math.log (2)) * target * target + 1) / 2);
        final long searchStart = (long) Math.sqrt(target);
//        final long searchStart = 302010;
        sieve.extend(target);
        Factors factors = new Factors(sieve);

        long search = searchStart;
        while (true) {
            List<Long> f = factors.factors(search);
            long sum = 0;
            for (final long fac : f) {
                sum += fac;
            }
            if (sum >= target) {
                return search;
            }
            search += 1;
        }
    }

    private long partTwoSlow() {
        final long target = (long) (Long.parseLong(loadResources().get(0)) / 11d) + 1;
        //TODO - There is a much better bound for this problem, but we need to write an inequality solver to use it.
//        final long searchStart = (long) Math.sqrt( ( (Math.log(target) / Math.log (2)) * target * target + 1) / 2);
        final long searchStart = (long) Math.sqrt(target);
//        final long searchStart = 302010;
        sieve.extend(target);
        Factors factors = new Factors(sieve);

        long search = searchStart;
        while (true) {
            List<Long> f = factors.factors(search);
            long sum = 0;
            for (final long fac : f) {
                if (search / fac <= PART_TWO_PRESENTS_LIMIT) {
                    sum += fac;
                }
            }
            if (sum >= target) {
                return search;
            }
            search += 1;
        }
    }
}
