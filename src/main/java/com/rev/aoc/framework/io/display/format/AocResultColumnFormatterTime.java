package com.rev.aoc.framework.io.display.format;

import com.rev.aoc.framework.problem.AocPart;
import com.rev.aoc.framework.problem.AocResult;

public final class AocResultColumnFormatterTime extends AocResultColumnFormatter {
    private final AocPart part;

    public AocResultColumnFormatterTime(final String header, int width, char padChar, final AocPart part) {
        super(header, width, padChar);
        this.part = part;
    }

    @Override
    protected String formatImpl(final AocResult result) {
        if (result.getError().isPresent()) {
            return "";
        }
        if (AocPart.ONE.equals(part)) {
            if (result.getPartOneTime().isEmpty()) {
                return "";
            }
            return Long.toString(result.getPartOneTime().get());
        }
        if (result.getPartTwoTime().isEmpty()) {
            return "";
        }
        return Long.toString(result.getPartTwoTime().get());
    }
}
