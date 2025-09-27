package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.ntheory.digit.Digit;
import com.rev.puzzles.math.ntheory.util.Pow;

import java.math.BigInteger;
import java.util.List;

public final class PE016 {

    @PeProblem(number = 16)
    @PeTestData(inputs = {"15"}, solutions = {"26"})
    public long powerDigitSum(final ProblemResourceLoader<List<String>> inputs) {
        return powerDigitSum(Long.parseLong(inputs.resources().get(0)));
    }

    private long powerDigitSum(final long exponent) {
        final BigInteger value = Pow.pow(new BigInteger("2"), exponent);
        return Digit.digitSum(value);
    }
}
