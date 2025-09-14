package com.rev.aoc.framework.problem;

import lombok.Getter;

import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public final class AocCoordinate implements ProblemCoordinate<AocCoordinate> {
    public static final String Y2K_PREFIX = "20";
    private final int year;
    private final int day;
    private final int part;

    private static final int Y2K = 2000;
    private static final int PARSER_BEGIN_INDEX = 0;
    private static final int YY_END_INDEX = 2;
    private static final int YYYY_END_INDEX = 4;


    private static final Comparator<AocCoordinate> COMPARATOR = comparator();
    private static final Pattern YY_MM_REGEX =
            Pattern.compile("([0-9]{2}):([0-9]{1,2}):([0-9])");
    private static final Pattern YYYY_MM_REGEX =
            Pattern.compile("([0-9]{4}):([0-9]{1,2}):([0-9])");

    public AocCoordinate(final int year, final int day, int part) {
        this.year = year;
        this.day = day;
        this.part = part;
    }

    private static Comparator<AocCoordinate> comparator() {
        return Comparator.<AocCoordinate>comparingInt(aoc -> aoc.year)
                .thenComparing(aoc -> aoc.day)
                .thenComparing(aoc -> aoc.part);
    }

    @Override
    public int compareTo(final AocCoordinate o) {
        return COMPARATOR.compare(this, o);
    }

    public static AocCoordinate parse(final String from) {
        final Matcher matcher;
        if (YY_MM_REGEX.matcher(from).find()) {
            matcher = YYYY_MM_REGEX.matcher(Y2K_PREFIX + from);
        } else if (YYYY_MM_REGEX.matcher(from).find()) {
            matcher = YYYY_MM_REGEX.matcher(from);
        } else {
            return null;
        }
        matcher.find();
        return new AocCoordinate(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)));
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s", year, day, part);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AocCoordinate that = (AocCoordinate) o;
        return year == that.year && day == that.day && part == that.part;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, day, part);
    }
}
