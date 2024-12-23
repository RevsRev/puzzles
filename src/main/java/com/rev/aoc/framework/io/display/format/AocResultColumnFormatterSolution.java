package com.rev.aoc.framework.io.display.format;

import com.rev.aoc.framework.problem.AocPart;
import com.rev.aoc.framework.problem.AocResult;

public final class AocResultColumnFormatterSolution extends AocResultColumnFormatter {

    private final AocPart part;

    public AocResultColumnFormatterSolution(final String header, int width, char padChar, final AocPart part) {
        super(header, width, padChar);
        this.part = part;
    }

    @Override
    protected String formatImpl(final AocResult<?, ?> result) {
        if (result.getError().isPresent()) {
            return "";
        }
        if (AocPart.ONE.equals(part)) {
            if (result.getPartOne().isEmpty()) {
                return "";
            }
            return result.getPartOne().toString();
        }
        if (result.getPartTwo().isEmpty()) {
            return "";
        }
        return result.getPartOne().toString();
    }
}
