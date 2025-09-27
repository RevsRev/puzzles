package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.ntheory.digit.Digit;
import com.rev.puzzles.math.ntheory.util.Factorial;

import java.math.BigInteger;
import java.util.List;

public final class PE020 {

    @PeProblem(number = 20)
    @PeTestData(inputs = {"10"}, solutions = {"27"})
    public long factorialDigitSum(final ProblemResourceLoader<List<String>> inputs) {
        return factorialDigitSum(new BigInteger(inputs.resources().get(0)));
    }

    private long factorialDigitSum(final BigInteger n) {
        final BigInteger factorial = Factorial.factorial(n);
        return Digit.digitSum(factorial);
    }
}
