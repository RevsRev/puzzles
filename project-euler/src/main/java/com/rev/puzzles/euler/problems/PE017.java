package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;

import java.util.List;

public final class PE017 {

    @PeProblem(number = 17)
    @PeTestData(inputs = {"5"}, solutions = {"19"})
    public long numberLetterCounts(final ProblemResourceLoader<List<String>> inputs) {
        return numberLetterCounts(Integer.parseInt(inputs.resources().get(0)));
    }

    private long numberLetterCounts(final int end) {
        long numberOfLetters = 0;
        for (int i = 1; i <= end; i++) {
            numberOfLetters += numberOfLetters(i);
        }
        return numberOfLetters;
    }

    private long numberOfLetters(final int n) {
        if (n == 1000) {
            // "onethousand"
            return 11;
        }

        long wordLength = 0;

        int hundreds = n / 100;
        int tens = (n - 100 * hundreds) / 10;
        int units = (n - 100 * hundreds - 10 * tens);

        if (hundreds > 0) {
            wordLength += unitWordLength(hundreds) + 7;
            if (tens > 0 || units > 0) {
                wordLength += 3;
            }
        }

        if (tens > 1) {
            wordLength += tenWordLength(tens);
        }

        if (tens == 1) {
            wordLength += teensWordLength(10 * tens + units);
        } else if (units > 0) {
            wordLength += unitWordLength(units);
        }
        return wordLength;
    }

    private long teensWordLength(final int teen) {
        switch (teen) {
            case 10 -> {
                return 3;
            }
            case 11, 12 -> {
                return 6;
            }
            case 13, 14, 18, 19 -> {
                return 8;
            }
            case 15, 16 -> {
                return 7;
            }
            case 17 -> {
                return 9;
            }
            default -> throw new IllegalArgumentException("Illegal argument: Teen: %s".formatted(teen));
        }
    }

    private long tenWordLength(final int tens) {
        switch (tens) {
            case 2, 3, 8, 9 -> {
                return 6;
            }
            case 4, 5, 6 -> {
                return 5;
            }
            case 7 -> {
                return 7;
            }
            default -> throw new IllegalArgumentException("Illegal argument: Ten: %s".formatted(tens));
        }
    }

    private long unitWordLength(final int unit) {
        switch (unit) {
            case 1, 2, 6 -> {
                return 3;
            }
            case 3, 7, 8 -> {
                return 5;
            }
            case 4, 5, 9 -> {
                return 4;
            }
            default -> {
                throw new IllegalArgumentException("Illegal Argument: Unit: %s".formatted(unit));
            }
        }
    }
}
