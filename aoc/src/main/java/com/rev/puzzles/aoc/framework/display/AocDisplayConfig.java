package com.rev.puzzles.aoc.framework.display;

import com.rev.puzzles.framework.framework.io.display.format.ResultColumnFormatter;

public final class AocDisplayConfig {

    private AocDisplayConfig() {
    }

    private static final char WHITE_SPACE = ' ';

    public static final ResultColumnFormatter[] AOC_RESULT_COLS = new ResultColumnFormatter[]{
            new AocResultColumnFormatterYear("Year", 6, WHITE_SPACE),
            new AocResultColumnFormatterDay("Day", 6, WHITE_SPACE),
            new AocResultColumnFormatterPart("Part", 6, WHITE_SPACE),
            new AocResultColumnFormatterSolution("Result", 45, WHITE_SPACE),
            new AocResultColumnFormatterTime("Time (ms)", 20, WHITE_SPACE),
            new AocResultColumnFormatterError("Error", 50, WHITE_SPACE),
    };

}
