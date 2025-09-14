package com.rev.aoc.framework.io.display.format;

import com.rev.aoc.framework.problem.ProblemCoordinate;
import com.rev.aoc.framework.problem.ProblemResult;

public abstract class ResultColumnFormatter<C extends ProblemCoordinate<C>> extends
        ColumnFormatter<ProblemResult<C, ?>> {

    protected static final String BLACK = "0m";
    protected static final String RED = "31m";
    protected static final String GREEN = "32m";

    public ResultColumnFormatter(final String header, int width, char padChar) {
        super(header, width, padChar);
    }

    protected abstract String formatImpl(ProblemResult<C, ?> result);

    /**
     * Override this to change the colour of a cell based on its contents :)
     */
    protected String getColor(final ProblemResult<C, ?> result) {
        return BLACK;
    }
}
