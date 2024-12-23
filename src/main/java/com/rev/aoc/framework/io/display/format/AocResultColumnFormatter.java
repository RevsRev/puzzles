package com.rev.aoc.framework.io.display.format;

import com.rev.aoc.framework.problem.AocResult;

public abstract class AocResultColumnFormatter extends ColumnFormatter<AocResult<?, ?>> {

    protected static final String BLACK = "0m";
    protected static final String RED = "31m";
    protected static final String GREEN = "32m";

    public AocResultColumnFormatter(final String header, int width, char padChar) {
        super(header, width, padChar);
    }

    protected abstract String formatImpl(AocResult<?, ?> result);

    /**
     * Override this to change the colour of a cell based on its contents :)
     */
    protected String getColor(final AocResult<?, ?> result) {
        return BLACK;
    }
}
