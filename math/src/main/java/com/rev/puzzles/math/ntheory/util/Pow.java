package com.rev.puzzles.math.ntheory.util;

public final class Pow {
    private Pow() {
    }

    public static long pow(long n, long pow) {
        long result = 1;
        long bit = 0;
        long twoToTheBit = 1;
        long nRaisedToPow2 = n;
        while (twoToTheBit <= pow) {
            if ((pow & twoToTheBit) >> bit == 1) {
                result = (result * nRaisedToPow2);
            }
            nRaisedToPow2 = (nRaisedToPow2 * nRaisedToPow2);
            bit += 1;
            twoToTheBit = 2 * twoToTheBit;
        }
        return result;
    }
}
