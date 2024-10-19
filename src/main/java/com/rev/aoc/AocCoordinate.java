package com.rev.aoc;

import java.util.Comparator;
import java.util.regex.Pattern;

public class AocCoordinate implements Comparable<AocCoordinate>
{
    public final int year;
    public final int day;

    private static final Comparator<AocCoordinate> COMPARATOR = comparator();
    private static final Pattern YY_MM_REGEX = Pattern.compile("[0-9]{2}:[0-9]{1,2}");
    private static final Pattern YYYY_MM_REGEX = Pattern.compile("[0-9]{4}:[0-9]{1,2}");

    public AocCoordinate(int year, int day)
    {
        this.year = year;
        this.day = day;
    }

    private static Comparator<AocCoordinate> comparator() {
        return Comparator.<AocCoordinate>comparingInt(aoc -> aoc.year).thenComparing(aoc -> aoc.day);
    }

    @Override
    public int compareTo(AocCoordinate o)
    {
        return COMPARATOR.compare(this, o);
    }

    public static AocCoordinate parse(String from) {
        if (YY_MM_REGEX.matcher(from).matches()) {
            int yy = Integer.parseInt(from.substring(0,2));
            int dd = Integer.parseInt(from.substring(3));
            return new AocCoordinate(2000 + yy, dd);
        }
        if (YYYY_MM_REGEX.matcher(from).matches()) {
            int yyyy = Integer.parseInt(from.substring(0,4));
            int dd = Integer.parseInt(from.substring(5));
            return new AocCoordinate(yyyy, dd);
        }
        return null;
    }
}
