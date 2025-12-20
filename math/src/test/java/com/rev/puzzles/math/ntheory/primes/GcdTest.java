package com.rev.puzzles.math.ntheory.primes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static com.rev.puzzles.math.ntheory.primes.Gcd.gcd;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GcdTest {

    @Test
    void testGcds() {
        Assertions.assertAll(
                () -> assertEquals(2, gcd(4, 2)),
                () -> assertEquals(1, gcd(11, 3)),
                () -> assertEquals(3, gcd(15, 21)),
                () -> assertEquals(10, gcd(100, 1010)),
                () -> assertEquals(17, gcd(17, 17)),
                () -> assertEquals(1, gcd(2, 1))
        );
    }

    @Test
    void testGcdsBigInt() {
        Assertions.assertAll(
                () -> assertEquals(BigInteger.valueOf(2), gcd(BigInteger.valueOf(4), BigInteger.valueOf(2))),
                () -> assertEquals(BigInteger.valueOf(1), gcd(BigInteger.valueOf(11), BigInteger.valueOf(3))),
                () -> assertEquals(BigInteger.valueOf(3), gcd(BigInteger.valueOf(15), BigInteger.valueOf(21))),
                () -> assertEquals(BigInteger.valueOf(10), gcd(BigInteger.valueOf(100), BigInteger.valueOf(1010))),
                () -> assertEquals(BigInteger.valueOf(17), gcd(BigInteger.valueOf(17), BigInteger.valueOf(17))),
                () -> assertEquals(BigInteger.valueOf(1), gcd(BigInteger.valueOf(2), BigInteger.valueOf(1)))
        );
    }
}