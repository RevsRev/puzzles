package com.rev.puzzles.leet.framework;

import com.rev.puzzles.framework.framework.problem.ProblemCoordinate;

import java.util.Comparator;
import java.util.Objects;

public record LeetCoordinate(int number) implements ProblemCoordinate<LeetCoordinate> {
    private static final Comparator<LeetCoordinate> COMPARATOR = comparator();

    private static Comparator<LeetCoordinate> comparator() {
        return Comparator.comparingInt(LeetCoordinate::number);
    }

    public static LeetCoordinate parse(final String from) {
        try {
            final int number = Integer.parseInt(from);
            return new LeetCoordinate(number);
        } catch (final Exception e) {
            return null;
        }
    }

    @Override
    public int compareTo(final LeetCoordinate o) {
        return COMPARATOR.compare(this, o);
    }

    @Override
    public String toString() {
        return String.format("%03d", number);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LeetCoordinate that = (LeetCoordinate) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }
}
