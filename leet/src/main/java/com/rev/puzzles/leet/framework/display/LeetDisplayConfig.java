package com.rev.puzzles.leet.framework.display;

import com.rev.puzzles.framework.framework.io.display.format.ResultColumnFormatter;

public final class LeetDisplayConfig {

    private static final char WHITE_SPACE = ' ';
    public static final ResultColumnFormatter[] LEET_RESULT_COLS =
            new ResultColumnFormatter[]{new LeetResultColumnFormatterNumber("Number", 6, WHITE_SPACE),
                    new LeetResultColumnFormatterSolution("Result", 45, WHITE_SPACE),
                    new LeetResultColumnFormatterTime("Time (ms)", 20, WHITE_SPACE),
                    new LeetResultColumnFormatterError("Error", 50, WHITE_SPACE)};

    private LeetDisplayConfig() {
    }

}
