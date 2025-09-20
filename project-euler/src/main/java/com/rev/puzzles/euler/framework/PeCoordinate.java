package com.rev.puzzles.euler.framework;

import com.rev.puzzles.framework.framework.problem.ProblemCoordinate;

import java.util.Comparator;
import java.util.Objects;

public record PeCoordinate(int number) implements ProblemCoordinate<PeCoordinate> {
    private static final Comparator<PeCoordinate> COMPARATOR = comparator();

    private static Comparator<PeCoordinate> comparator() {
        return Comparator.comparingInt(PeCoordinate::number);
    }

    public static PeCoordinate parse(final String from) {
        try {
            final int number = Integer.parseInt(from);
            return new PeCoordinate(number);
        } catch (final Exception e) {
            return null;
        }
    }

    @Override
    public int compareTo(final PeCoordinate o) {
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
        final PeCoordinate that = (PeCoordinate) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }
}
