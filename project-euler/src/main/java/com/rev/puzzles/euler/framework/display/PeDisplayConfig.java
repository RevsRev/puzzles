package com.rev.puzzles.euler.framework.display;

import com.rev.puzzles.framework.framework.io.display.format.ResultColumnFormatter;

public final class PeDisplayConfig {

    private static final char WHITE_SPACE = ' ';
    public static final ResultColumnFormatter[] PE_RESULT_COLS =
            new ResultColumnFormatter[]{new PeResultColumnFormatterNumber("Number", 6, WHITE_SPACE),
                    new PeResultColumnFormatterSolution("Result", 45, WHITE_SPACE),
                    new PeResultColumnFormatterTime("Time (ms)", 20, WHITE_SPACE),
                    new PeResultColumnFormatterError("Error", 50, WHITE_SPACE)};

    private PeDisplayConfig() {
    }

}
