package com.rev.aoc.util.math.ntheory.primes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("checkstyle:MagicNumber")
public final class Gcd {

    private Gcd() {
    }

    public static long gcd(long a, long b) {
        List<long[]> steps = euclid(a, b);
        if (steps.isEmpty()) {
            return -1;
        }
        return steps.get(steps.size() - 1)[3];
    }

    public static List<long[]> euclid(long a, long b) {
        if (b < a) {
            return euclid(b, a);
        }

        if (a <= 0 || b <= 0) {
            return new ArrayList<>();
        }

        List<long[]> retval = new ArrayList<>();
        do {
            long r = a % b;
            long q = a / b;
            retval.add(new long[] {a, b, q, r});
            a = b;
            b = r;
        } while (b != 0);
        return retval;
    }


    public static BigInteger gcd(final BigInteger a, final BigInteger b) {
        List<BigInteger[]> steps = euclid(a, b);
        if (steps.isEmpty()) {
            return BigInteger.ZERO.subtract(BigInteger.ONE);
        }
        return steps.get(steps.size() - 1)[3];
    }

    @SuppressWarnings("checkstyle:FinalParameters")
    public static List<BigInteger[]> euclid(BigInteger a,
                                            BigInteger b) {
        if (b.compareTo(a) > 0) {
            return euclid(b, a);
        }

        if (a.compareTo(BigInteger.ZERO) <= 0 || b.compareTo(BigInteger.ZERO) <= 0) {
            return new ArrayList<>();
        }

        List<BigInteger[]> retval = new ArrayList<>();
        do {
            BigInteger r = a.mod(b);
            BigInteger q = a.divide(b);
            retval.add(new BigInteger[] {a, b, q, r});
            a = b;
            b = r;
        } while (b.compareTo(BigInteger.ZERO) != 0);
        return retval;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static BigInteger gcd(final List<BigInteger[]> euclidSteps) {
        if (euclidSteps.isEmpty()) {
            return BigInteger.valueOf(-1L);
        }
        return euclidSteps.get(euclidSteps.size() - 1)[1];
    }

}
