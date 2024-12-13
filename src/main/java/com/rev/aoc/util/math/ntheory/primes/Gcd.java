package com.rev.aoc.util.math.ntheory.primes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class Gcd {

    private Gcd() {
    }

    public static long euclid(long a, long b) {
        if (a < b) {
            long c = a;
            a = b;
            b = c;
        }

        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }

        long rem = a % b;
        while (rem != 0) {
            a = b;
            b = rem;
            rem = a % b;
        }
        return b;
    }

    /**
     * Returns the steps of Euclid's algorithm as a list of arrays of the form [a,b,q,r]
     *
     * <p>If the input is invalid, then an empty result is returned.
     */
    @SuppressWarnings("checkstyle:FinalParameters")
    public static List<BigInteger[]> euclid(BigInteger a,
                                            BigInteger b) {
        if (b.compareTo(a) == 1) {
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
