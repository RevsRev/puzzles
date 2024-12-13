package com.rev.aoc.util.math.ntheory.eq.diophantine;

import com.rev.aoc.util.math.ntheory.primes.Gcd;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class DiophantineSolver {

    private final BigInteger a;
    private final BigInteger b;
    private final BigInteger c;
    private Optional<BigInteger[]> solution;

    public DiophantineSolver(final BigInteger a,
                             final BigInteger b,
                             final BigInteger c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public boolean canSolve() {
        if (solution == null) {
            solve();
        }
        return !solution.isEmpty();
    }
    public Optional<BigInteger[]> solve() {
        if (solution == null) {
            solution = solveDiophantine(a, b, c);
        }
        return solution;
    }


    /**
     * Solves linear diophantine equations of the form a*x + b*y = c
     *
     * <p>If the equation has no solution, an empty optional is returned.
     *
     * <p>Else, the long[] contained in the optional is of the form [X,Y,xInc,yInc], where (X,Y) is
     * the principal solution to the equation, and xInc, yInc are the increments for the general
     * solution of the form:
     *
     * <p>x = X + k*xInc y = Y + k*yInc
     *
     * <p>For k an integer.
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    public static Optional<BigInteger[]> solveDiophantine(final BigInteger a,
                                                          final BigInteger b,
                                                          final BigInteger c) {
        boolean aNeg = a.compareTo(BigInteger.ZERO) < 0;
        boolean bNeg = b.compareTo(BigInteger.ZERO) < 0;
        Optional<BigInteger[]> result = solveDiophantinePositive(a.abs(), b.abs(), c);

        if (result.isEmpty() || (!aNeg && !bNeg)) {
            return result;
        }

        BigInteger[] solution = result.get();
        if (aNeg) {
            solution[0] = solution[0].multiply(BigInteger.valueOf(-1));
        }
        if (bNeg) {
            solution[1] = solution[1].multiply(BigInteger.valueOf(-1));
        }
        return Optional.of(solution);
    }

    /** Solves diophantine equation with a,b both positive */
    private static Optional<BigInteger[]> solveDiophantinePositive(
            final BigInteger a,
            final BigInteger b,
            final BigInteger c) {
        if (a.compareTo(b) < 0) {
            Optional<BigInteger[]> result = solveDiophantinePositive(b, a, c);
            if (result.isEmpty()) {
                return result;
            }
            BigInteger[] res = result.get();
            BigInteger[] swappedRes = new BigInteger[4];
            swappedRes[0] = res[1];
            swappedRes[1] = res[0];
            swappedRes[2] = res[3];
            swappedRes[3] = res[2];
            return Optional.of(swappedRes);
        }

        List<BigInteger[]> euclidSteps = Gcd.euclid(a, b);
        BigInteger gcdab = Gcd.gcd(euclidSteps);
        if (c.mod(gcdab).compareTo(BigInteger.ZERO) != 0) {
            return Optional.empty();
        }

        BigInteger factor = c.divide(gcdab);

        // case a = kb for some k, gcd(a,b) = b
        if (euclidSteps.size() == 1) {
            BigInteger x = BigInteger.ZERO;
            BigInteger y = (a.divide(b)).multiply(factor);
            BigInteger xInc = factor;
            BigInteger yInc = (a.divide(b)).multiply(factor);
            return Optional.of(new BigInteger[] {a, b, xInc, yInc});
        }

        BigInteger[] steps = euclidSteps.get(euclidSteps.size() - 2);

        // a_{k-1} = b_{k} - q_{k-2}a_{k}
        // b_{k-1} = a_{k}

        // Initially, a_{n} = -q_{n-1}, b_n = 1
        BigInteger ak = steps[2].multiply(BigInteger.valueOf(-1));
        BigInteger bk = BigInteger.ONE;

        for (int i = euclidSteps.size() - 3; i >= 0; i--) {
            BigInteger[] step = euclidSteps.get(i);
            BigInteger akk = ak.multiply(BigInteger.ONE);
            ak = bk.subtract(step[2].multiply(ak));
            bk = akk;
        }

        // principal solution is final values ak & bk : d = ak*a + bk*b
        BigInteger x = bk.multiply(factor);
        BigInteger y = ak.multiply(factor);
        BigInteger xInc = b.divide(gcdab);
        BigInteger yInc = (a.multiply(BigInteger.valueOf(-1))).divide(gcdab);

        return Optional.of(new BigInteger[] {x, y, xInc, yInc});
    }

    /**
     * Solves for x satisfying
     *
     * <p>x = a1 (mod b1) x = a2 (mod b2) ... x = aN (mod bN)
     *
     * @param congruences - Map of mod value to remainder (i.e. b1 --> a1, b2 --> a2, ..., bN --> aN)
     * @return
     */
    public static Optional<BigInteger[]> solveChineseRemainders(
            final Map<BigInteger, BigInteger> congruences) {

        Iterator<BigInteger> itMods = congruences.keySet().iterator();
        if (!itMods.hasNext()) {
            return Optional.empty();
        }

        BigInteger firstMod = itMods.next();
        BigInteger[] solution = new BigInteger[2];
        solution[0] = congruences.get(firstMod);
        solution[1] = firstMod;

        while (itMods.hasNext()) {
            BigInteger thisValue = solution[0];
            BigInteger thisMod = solution[1];

            BigInteger nextMod = itMods.next();
            BigInteger nextValue = congruences.get(nextMod);

            BigInteger[] diophantineParams =
                    new BigInteger[] {
                            thisMod, nextMod.multiply(BigInteger.valueOf(-1)), nextValue.subtract(thisValue)
                    };
            Optional<BigInteger[]> result =
                    solveDiophantine(diophantineParams[0], diophantineParams[1], diophantineParams[2]);
            if (result.isEmpty()) {
                return Optional.empty();
            }
            BigInteger sol = thisValue.add(result.get()[0].multiply(thisMod));
            BigInteger increment = thisMod.multiply(nextMod);
            if (sol.compareTo(increment) > 0) {
                sol = sol.mod(increment);
            }
            while (sol.compareTo(BigInteger.ZERO) < 0) {
                sol = sol.add(increment);
            }
            solution[0] = sol;
            solution[1] = increment;
        }
        return Optional.of(solution);
    }

}
