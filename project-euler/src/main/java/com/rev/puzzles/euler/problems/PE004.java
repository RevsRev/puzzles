package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.ntheory.util.Pow;
import com.rev.puzzles.math.perm.Palindrome;

import java.util.List;

public final class PE004 {

    @PeProblem(number = 4)
    @PeTestData(inputs = {"2"}, solutions = {"9009"})
    public long largestProductPalindrome(final ProblemResourceLoader<List<String>> inputs) {
        return largestProductPalindrome(Long.parseLong(inputs.resources().get(0)));
    }

    private long largestProductPalindrome(final long n) {
        long largestPalindromeFound = 0;
        final long limit = Pow.pow(10, n);
        for (long i = 0; i < limit; i++) {
            for (long j = i; j < limit; j++) {
                final long result = i * j;
                if (result > largestPalindromeFound && Palindrome.isPalindromeBase10(result)) {
                    largestPalindromeFound = result;
                }
            }
        }
        return largestPalindromeFound;
    }

}
