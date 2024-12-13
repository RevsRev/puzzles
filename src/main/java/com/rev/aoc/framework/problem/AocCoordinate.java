package com.rev.aoc.framework.problem;

import lombok.Getter;

import java.util.Comparator;
import java.util.regex.Pattern;

@Getter
public final class AocCoordinate implements Comparable<AocCoordinate> {
    private final int year;
    private final int day;

    private static final int Y2K = 2000;
    private static final int PARSER_BEGIN_INDEX = 0;
    private static final int YY_END_INDEX = 2;
    private static final int YYYY_END_INDEX = 4;


    private static final Comparator<AocCoordinate> COMPARATOR = comparator();
    private static final Pattern YY_MM_REGEX =
            Pattern.compile("[0-9]{2}:[0-9]{1,2}");
    private static final Pattern YYYY_MM_REGEX =
            Pattern.compile("[0-9]{4}:[0-9]{1,2}");

    public AocCoordinate(final int year, final int day) {
        this.year = year;
        this.day = day;
    }

    private static Comparator<AocCoordinate> comparator() {
        return Comparator.<AocCoordinate>comparingInt(aoc -> aoc.year)
                .thenComparing(aoc -> aoc.day);
    }

    @Override
    public int compareTo(final AocCoordinate o) {
        return COMPARATOR.compare(this, o);
    }

    public static AocCoordinate parse(final String from) {
        if (YY_MM_REGEX.matcher(from).matches()) {
            String yyString = from.substring(PARSER_BEGIN_INDEX, YY_END_INDEX);
            int yy = Integer.parseInt(yyString);
            int dd = Integer.parseInt(from.substring(YY_END_INDEX + 1));
            return new AocCoordinate(Y2K + yy, dd);
        }
        if (YYYY_MM_REGEX.matcher(from).matches()) {
            String yyyyStr = from.substring(PARSER_BEGIN_INDEX, YYYY_END_INDEX);
            int yyyy = Integer.parseInt(yyyyStr);
            int dd = Integer.parseInt(from.substring(YYYY_END_INDEX + 1));
            return new AocCoordinate(yyyy, dd);
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", year, day);
    }
}
