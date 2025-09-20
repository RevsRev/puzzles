package com.rev.puzzles.math.ntheory.modular;

public final class Mod {

    private Mod() {
    }

    public static long pow(long n, long pow, long modulus) {
        long result = 1;
        long bit = 0;
        long twoToTheBit = 1;
        long nRaisedToPow2 = n % modulus;
        while (twoToTheBit <= pow) {
            if ((pow & twoToTheBit) >> bit == 1) {
                result = (result * nRaisedToPow2) % modulus;
            }
            nRaisedToPow2 = (nRaisedToPow2 * nRaisedToPow2) % modulus;
            bit += 1;
            twoToTheBit = 2 * twoToTheBit;
        }
        return result;
    }
}
