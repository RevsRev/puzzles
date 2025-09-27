package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;

import java.util.List;

public final class PE019 {

    private static final int JANUARY = 0;
    private static final int FEBRUARY = 1;
//    private static final int MARCH = 2;
//    private static final int APRIL = 3;
//    private static final int MAY = 4;
//    private static final int JUNE = 5;
//    private static final int JULY = 6;
//    private static final int AUGUST = 7;
//    private static final int SEPTEMBER = 8;
//    private static final int OCTOBER = 9;
//    private static final int NOVEMBER = 10;
//    private static final int DECEMBER = 11;

    private static final int[] DAYS_IN_MONTH = new int[]{
            31, //Jan
            28, //Feb
            31, //March
            30, //April
            31, //May
            30, //June
            31, //July
            31, //Aug
            30, //Sep
            31, //Oct
            30, //Nov
            31  //Dec
    };

    @PeProblem(number = 19)
    public int countingSundays(final ProblemResourceLoader<List<String>> inputs) {
        int sundays = 0;
        int day = 0; //1st Jan 1900 (Monday)
        int month = JANUARY;
        int year = 1900;

        while (year <= 2000) {
            if (day % 7 == 6 && year > 1900) {
                sundays++;
            }

            int daysInMonth = DAYS_IN_MONTH[month];
            if (month == FEBRUARY && year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                daysInMonth += 1;
            }

            day += daysInMonth;
            month = (month + 1) % 12;

            if (month == JANUARY) {
                year++;
            }
        }

        return sundays;
    }
}
